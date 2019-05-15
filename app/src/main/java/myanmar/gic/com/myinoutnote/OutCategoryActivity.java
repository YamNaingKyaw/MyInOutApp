package myanmar.gic.com.myinoutnote;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import myanmar.gic.com.myinoutnote.ViewModels.OutCategoryViewModel;

public class OutCategoryActivity extends AppCompatActivity {
    public static String NEW_OUT_CATEGORY_REPLY = "out category reply";
    private ActionBar toolBar;
    private OutCategoryViewModel mOutCategoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_category);

        toolBar = getSupportActionBar();
        toolBar.setTitle("Out Category");
        toolBar.setDisplayHomeAsUpEnabled(true);

        mOutCategoryViewModel = ViewModelProviders.of(this).get(OutCategoryViewModel.class);

        final EditText etOutCategory = (EditText) findViewById(R.id.et_outcategory);

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (!etOutCategory.getText().toString().equalsIgnoreCase("")) {
                    replyIntent.putExtra(NEW_OUT_CATEGORY_REPLY, etOutCategory.getText().toString());
                    setResult(RESULT_OK, replyIntent);
                } else {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                finish();
            }
        });
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
