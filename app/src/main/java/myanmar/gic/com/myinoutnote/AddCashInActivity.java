package myanmar.gic.com.myinoutnote;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import myanmar.gic.com.myinoutnote.Models.CashIn;
import myanmar.gic.com.myinoutnote.Models.InCategory;
import myanmar.gic.com.myinoutnote.ViewModels.CashInViewModel;
import myanmar.gic.com.myinoutnote.ViewModels.InCategoryViewModel;

import static myanmar.gic.com.myinoutnote.Adapters.CashInAdapter.UPDATE_DELETE_AMOUNT;
import static myanmar.gic.com.myinoutnote.Adapters.CashInAdapter.UPDATE_DELETE_CATEGORY;
import static myanmar.gic.com.myinoutnote.Adapters.CashInAdapter.UPDATE_DELETE_DATE;
import static myanmar.gic.com.myinoutnote.Adapters.CashInAdapter.UPDATE_DELETE_ID;
import static myanmar.gic.com.myinoutnote.InCategoryActivity.NEW_IN_CATEGORY_REPLY;

public class AddCashInActivity extends AppCompatActivity {
    private ActionBar toolBar;
    private InCategoryViewModel mInCategoryViewModel;
    private int NEW_IN_CATEGORY_REQUEST_CODE = 1;
    private CashInViewModel mCashInViewModel;

    ArrayAdapter<String> adapter;

    public static String ADD_CATEGORY_REPLY_DATE = "cash in replay date";
    public static String ADD_CATEGORY_REPLY_CATEGORY = "cash in replay category";
    public static String ADD_CATEGORY_REPLY_AMOUNT = "cash in replay amount";

    int updateId = 0;
    String updateDate = "";
    String updateCategory = "";
    String updateAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cash_in);

        toolBar = getSupportActionBar();
        toolBar.setTitle("Add Cash In Amount");
        toolBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        updateId = intent.getIntExtra(UPDATE_DELETE_ID, 0);
        updateDate = intent.getStringExtra(UPDATE_DELETE_DATE);
        updateCategory = intent.getStringExtra(UPDATE_DELETE_CATEGORY);
        updateAmount = intent.getStringExtra(UPDATE_DELETE_AMOUNT);

        final EditText etDate = (EditText) findViewById(R.id.et_date);
        etDate.setFocusable(false);
        etDate.setKeyListener(null);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });


        ImageButton btnNewInCategory = (ImageButton) findViewById(R.id.btn_newincategory);
        btnNewInCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goIntent = new Intent(AddCashInActivity.this, InCategoryActivity.class);
                startActivityForResult(goIntent, NEW_IN_CATEGORY_REQUEST_CODE);
            }
        });

        final Spinner spInCategory = (Spinner) findViewById(R.id.sp_incategory);

        mInCategoryViewModel = ViewModelProviders.of(this).get(InCategoryViewModel.class);
        mInCategoryViewModel.getmAllInCategory().observe(this, new Observer<List<InCategory>>() {
            @Override
            public void onChanged(@Nullable List<InCategory> inCategories) {
                List<String> mAllInCategory = new ArrayList<>();

                if (inCategories.size() > 0) {
                    for (int i = 0; i < inCategories.size(); i++) {
                        mAllInCategory.add(inCategories.get(i).getInCtg());
                    }
                }

                adapter = new ArrayAdapter<>(getApplication(), R.layout.custom_dropdownlist, mAllInCategory);
                spInCategory.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                // For Update Category
                if (updateCategory != null) {
                    int spinnerPosition = adapter.getPosition(updateCategory);
                    spInCategory.setSelection(spinnerPosition);
                }
            }
        });

        final EditText etCashInAmount = (EditText) findViewById(R.id.et_cashinamount);

        final Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryType = spInCategory.getSelectedItem().toString();

                Intent replyIntent = new Intent();

                if (btnSave.getText().equals("Save")) {
                    if (!etDate.getText().toString().equalsIgnoreCase("") && !categoryType.equalsIgnoreCase("") && !etCashInAmount.getText().toString().equalsIgnoreCase("")) {
                        replyIntent.putExtra(ADD_CATEGORY_REPLY_DATE, etDate.getText().toString());
                        replyIntent.putExtra(ADD_CATEGORY_REPLY_CATEGORY, categoryType);
                        replyIntent.putExtra(ADD_CATEGORY_REPLY_AMOUNT, etCashInAmount.getText().toString());
                        setResult(RESULT_OK, replyIntent);
                    } else {
                        setResult(RESULT_CANCELED, replyIntent);
                    }
                    finish();
                } else {
                    if (!etDate.getText().toString().equalsIgnoreCase("") && !categoryType.equalsIgnoreCase("") && !etCashInAmount.getText().toString().equalsIgnoreCase("")) {
                        CashIn cashOut = new CashIn(updateId, etDate.getText().toString(), categoryType, etCashInAmount.getText().toString());
                        mCashInViewModel.updateCashIn(cashOut);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Fill Data Complete", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mCashInViewModel = ViewModelProviders.of(this).get(CashInViewModel.class);

        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCashInViewModel.deleteCashIn(updateId);
                finish();
            }
        });
        btnDelete.setVisibility(View.INVISIBLE);

        if (updateDate != null) {
            btnSave.setText("Update");
            btnDelete.setVisibility(View.VISIBLE);
        }

        //Intent Update
        etDate.setText(updateDate);

        etCashInAmount.setText(updateAmount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NEW_IN_CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            InCategory inCategory = new InCategory(data.getStringExtra(NEW_IN_CATEGORY_REPLY));
            mInCategoryViewModel.insertInCategory(inCategory);
            Toast.makeText(getApplicationContext(), "Save Successful", Toast.LENGTH_SHORT).show();
        } else if (requestCode == NEW_IN_CATEGORY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Data Save Fail", Toast.LENGTH_SHORT).show();
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
