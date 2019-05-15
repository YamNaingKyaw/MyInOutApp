package myanmar.gic.com.myinoutnote;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import myanmar.gic.com.myinoutnote.Models.Profile;
import myanmar.gic.com.myinoutnote.ViewModels.ProfileViewModel;

public class EditProfileActivity extends AppCompatActivity {
    private ActionBar toolBar;
    private ProfileViewModel mProfileViewModel;

    EditText etName, etNRC, etGender, etAddress, etPhone;

    CircleImageView ibtnUserPhoto;

    byte[] img;

    String updateName = "";

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolBar = getSupportActionBar();
        toolBar.setDisplayHomeAsUpEnabled(true);

        etName = (EditText) findViewById(R.id.et_name);
        etNRC = (EditText) findViewById(R.id.et_nrc);
        etGender = (EditText) findViewById(R.id.et_gender);
        etAddress = (EditText) findViewById(R.id.et_address);
        etPhone = (EditText) findViewById(R.id.et_phone);

        ibtnUserPhoto = (CircleImageView) findViewById(R.id.user_profile_photo);
        ibtnUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        Bitmap bitmap = ((BitmapDrawable) ibtnUserPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        img = baos.toByteArray();

        Button btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _name = etName.getText().toString();
                String _nrc = etNRC.getText().toString();
                String _gender = etGender.getText().toString();
                String _address = etAddress.getText().toString();
                String _phone = etPhone.getText().toString();

                Profile updateProfile = new Profile(_name, _nrc, _gender, _address, _phone, img);

                mProfileViewModel.updateProfile(updateProfile, updateName);

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        mProfileViewModel.getAllProfile().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(@Nullable List<Profile> profiles) {
                if (profiles.size() > 0) {
                    Profile mProfile = profiles.get(0);
                    updateUI(mProfile);
                } else {
                    etName.setText("");
                    etNRC.setText("");
                    etGender.setText("");
                    etAddress.setText("");
                    etPhone.setText("");
                }
            }
        });
    }

    private Bitmap getBitMap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    private void updateUI(Profile mProfile) {
        ibtnUserPhoto.setImageBitmap(getBitMap(mProfile.getImage()));
        updateName = mProfile.getName();
        etName.setText(mProfile.getName());
        etNRC.setText(mProfile.getNrc());
        etGender.setText(mProfile.getGender());
        etAddress.setText(mProfile.getAddress());
        etPhone.setText(mProfile.getPhone());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ibtnUserPhoto.setImageBitmap(bitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img = bos.toByteArray();
            } catch (IOException e) {
                Log.i("TAG", "Some exception " + e);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
