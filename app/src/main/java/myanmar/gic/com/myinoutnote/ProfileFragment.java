package myanmar.gic.com.myinoutnote;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import myanmar.gic.com.myinoutnote.Models.Profile;
import myanmar.gic.com.myinoutnote.ViewModels.ProfileViewModel;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private int EDIT_REQUEST_CODE = 1;

    CircleImageView ibtnUserPhoto;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Boolean mfirst;

    ProfileViewModel mProfileViewModel;

    TextView tvUserProfileName, tvNrc, tvGender, tvAddress, tvPhone;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        tvUserProfileName = (TextView) v.findViewById(R.id.tv_user_profile_name);
        tvNrc = (TextView) v.findViewById(R.id.tv_nrc);
        tvGender = (TextView) v.findViewById(R.id.tv_gender);
        tvAddress = (TextView) v.findViewById(R.id.tv_address);
        tvPhone = (TextView) v.findViewById(R.id.tv_phone);

        sp = getActivity().getSharedPreferences("SP_MYINOUT_APP", MODE_PRIVATE);
        mfirst = sp.getBoolean("firstTime", true);

        ibtnUserPhoto = (CircleImageView) v.findViewById(R.id.user_profile_photo);

        Button btnEdit = (Button) v.findViewById(R.id.btn_profileedit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goIntent = new Intent(getContext(), EditProfileActivity.class);
                startActivityForResult(goIntent, EDIT_REQUEST_CODE);
            }
        });

        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        if (mfirst) {

            CircleImageView imageView = (CircleImageView) v.findViewById(R.id.user_profile_photo);
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageInByte = baos.toByteArray();

            mProfileViewModel.insertProfile(new Profile("Your Name", "Your NRC Card", "Male / Female", "Your Address", "09-", imageInByte));

            editor = sp.edit();
            mfirst = false;
            editor.putBoolean("firstTime", mfirst);
            editor.commit();
        }

        getAllProfile();

        return v;
    }

    private void getAllProfile() {
        mProfileViewModel.getAllProfile().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(@Nullable List<Profile> profiles) {
                if (profiles.size() > 0) {
                    updateProfile(profiles.get(0));
                }
            }
        });
    }

    private void updateProfile(Profile profile) {
        ibtnUserPhoto.setImageBitmap(getBitMap(profile.getImage()));
        tvUserProfileName.setText(profile.getName());
        tvNrc.setText("User NRC : " + profile.getNrc());
        tvGender.setText("User Gender : " + profile.getGender());
        tvAddress.setText("User Address : " + profile.getAddress());
        tvPhone.setText("User Phone : " + profile.getPhone());
    }

    private Bitmap getBitMap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            getAllProfile();
        } else {
            Toast.makeText(getContext(), "Profile Update Fail", Toast.LENGTH_SHORT).show();
        }
    }
}
