package myanmar.gic.com.myinoutnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InCategoryActivity extends AppCompatActivity {
    public static String NEW_IN_CATEGORY_REPLY = "in category reply";
    private ActionBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_category);

        toolBar = getSupportActionBar();
        toolBar.setTitle("In Category");
        toolBar.setDisplayHomeAsUpEnabled(true);

        final EditText etInCategory = (EditText) findViewById(R.id.et_incategory);

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (!etInCategory.getText().toString().equalsIgnoreCase("")) {
                    replyIntent.putExtra(NEW_IN_CATEGORY_REPLY, etInCategory.getText().toString());
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
