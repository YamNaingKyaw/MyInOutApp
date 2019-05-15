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

import myanmar.gic.com.myinoutnote.Models.CashOut;
import myanmar.gic.com.myinoutnote.Models.OutCategory;
import myanmar.gic.com.myinoutnote.ViewModels.CashOutViewModel;
import myanmar.gic.com.myinoutnote.ViewModels.OutCategoryViewModel;

import static myanmar.gic.com.myinoutnote.Adapters.CashOutAdapter.UPDATE_DELETE_AMOUNT;
import static myanmar.gic.com.myinoutnote.Adapters.CashOutAdapter.UPDATE_DELETE_CATEGORY;
import static myanmar.gic.com.myinoutnote.Adapters.CashOutAdapter.UPDATE_DELETE_DATE;
import static myanmar.gic.com.myinoutnote.Adapters.CashOutAdapter.UPDATE_DELETE_ID;
import static myanmar.gic.com.myinoutnote.OutCategoryActivity.NEW_OUT_CATEGORY_REPLY;

public class AddCashOutActivity extends AppCompatActivity {
    private ActionBar toolBar;
    private OutCategoryViewModel mOutCategoryViewModel;
    private CashOutViewModel mCashOutViewModel;
    private int NEW_OUT_CATEGORY_REQUEST_CODE = 1;

    ArrayAdapter<String> adapter;

    public static String ADD_CATEGORY_OUT_REPLY_DATE = "cash out replay date";
    public static String ADD_CATEGORY_OUT_REPLY_CATEGORY = "cash out replay category";
    public static String ADD_CATEGORY_OUT_REPLY_AMOUNT = "cash out replay amount";

    int updateId = 0;
    String updateDate = "";
    String updateCategory = "";
    String updateAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cash_out);

        toolBar = getSupportActionBar();
        toolBar.setTitle("Add Cash Out Amoount");
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

        ImageButton btnNewOutCategory = (ImageButton) findViewById(R.id.btn_newoutcategory);
        btnNewOutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goIntent = new Intent(AddCashOutActivity.this, OutCategoryActivity.class);
                startActivityForResult(goIntent, NEW_OUT_CATEGORY_REQUEST_CODE);
            }
        });

        final Spinner spOutCategory = (Spinner) findViewById(R.id.sp_outcategory);

        mOutCategoryViewModel = ViewModelProviders.of(this).get(OutCategoryViewModel.class);
        mOutCategoryViewModel.getmAllOutCategory().observe(this, new Observer<List<OutCategory>>() {
            @Override
            public void onChanged(@Nullable List<OutCategory> outCategories) {
                List<String> mAllOutCategory = new ArrayList<>();

                if (outCategories.size() > 0) {
                    for (int i = 0; i < outCategories.size(); i++) {
                        mAllOutCategory.add(outCategories.get(i).getOutCtg());
                    }
                }

                adapter = new ArrayAdapter<>(getApplication(), R.layout.custom_dropdownlist, mAllOutCategory);
                spOutCategory.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                // For Update Category
                if (updateCategory != null) {
                    int spinnerPosition = adapter.getPosition(updateCategory);
                    spOutCategory.setSelection(spinnerPosition);
                }
            }
        });

        final EditText etAmount = (EditText) findViewById(R.id.et_cashoutamount);

        final Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = spOutCategory.getSelectedItem().toString();

                Intent replyIntent = new Intent();

                if (btnSave.getText().equals("Save")) {
                    if (!etDate.getText().toString().equalsIgnoreCase("") && !category.equalsIgnoreCase("") && !etAmount.getText().toString().equalsIgnoreCase("")) {
                        replyIntent.putExtra(ADD_CATEGORY_OUT_REPLY_DATE, etDate.getText().toString());
                        replyIntent.putExtra(ADD_CATEGORY_OUT_REPLY_CATEGORY, category);
                        replyIntent.putExtra(ADD_CATEGORY_OUT_REPLY_AMOUNT, etAmount.getText().toString());

                        setResult(RESULT_OK, replyIntent);
                    } else {
                        setResult(RESULT_CANCELED, replyIntent);
                    }
                    finish();
                } else {
                    if (!etDate.getText().toString().equalsIgnoreCase("") && !category.equalsIgnoreCase("") && !etAmount.getText().toString().equalsIgnoreCase("")) {
                        CashOut cashOut = new CashOut(updateId, etDate.getText().toString(), category, etAmount.getText().toString());
                        mCashOutViewModel.updateCashOut(cashOut);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Fill Data Complete", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mCashOutViewModel = ViewModelProviders.of(this).get(CashOutViewModel.class);

        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCashOutViewModel.deleteCashOut(updateId);
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

        etAmount.setText(updateAmount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == NEW_OUT_CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            OutCategory outCategory = new OutCategory(data.getStringExtra(NEW_OUT_CATEGORY_REPLY));
            mOutCategoryViewModel.insertOutCategory(outCategory);
            Toast.makeText(getApplicationContext(), "Save Successful", Toast.LENGTH_SHORT).show();
        } else if (requestCode == NEW_OUT_CATEGORY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
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
