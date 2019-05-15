package myanmar.gic.com.myinoutnote;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import myanmar.gic.com.myinoutnote.Adapters.CashInAdapter;
import myanmar.gic.com.myinoutnote.Models.CashIn;
import myanmar.gic.com.myinoutnote.ViewModels.CashInViewModel;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static myanmar.gic.com.myinoutnote.AddCashInActivity.ADD_CATEGORY_REPLY_AMOUNT;
import static myanmar.gic.com.myinoutnote.AddCashInActivity.ADD_CATEGORY_REPLY_CATEGORY;
import static myanmar.gic.com.myinoutnote.AddCashInActivity.ADD_CATEGORY_REPLY_DATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CashInFragment extends Fragment {
    private int NEW_IN_CATEGORY = 1;
    private CashInViewModel mCashInViewModel;
    private Calendar cal;

    TextView tvTotal;

    CashInAdapter adapter;

    public CashInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cash_in, container, false);

        cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_cash_in);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goIntent = new Intent(getActivity(), AddCashInActivity.class);
                startActivityForResult(goIntent, NEW_IN_CATEGORY);
            }
        });

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rcv_cash_in);
        adapter = new CashInAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCashInViewModel = ViewModelProviders.of(this).get(CashInViewModel.class);

        final TextView tvDate = (TextView) v.findViewById(R.id.tv_date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        tvDate.setText(day + "-" + (month + 1) + "-" + year);
        String dateString = day + "-" + (month + 1) + "-" + year;

        tvTotal = (TextView) v.findViewById(R.id.tv_total);

        final ImageButton ibtnRight = (ImageButton) v.findViewById(R.id.ibtn_right);
        ibtnRight.setEnabled(false);
        ibtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                Calendar today = Calendar.getInstance();
                Date date = new Date();
                today.setTime(date);

                if ((cal.get(Calendar.DAY_OF_YEAR) <= today.get(Calendar.DAY_OF_YEAR)) && (cal.get(Calendar.YEAR) <= today.get(Calendar.YEAR))) {
                    tvDate.setText(day + "-" + (month + 1) + "-" + year);
                    String dateString = day + "-" + (month + 1) + "-" + year;

//                getAdapterData(dateString);

                    mCashInViewModel.dateCashIn(dateString).observe(getActivity(), new Observer<List<CashIn>>() {
                        @Override
                        public void onChanged(@Nullable List<CashIn> cashIns) {
                            adapter.setCashIns(cashIns);
                        }
                    });

                    mCashInViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer integer) {
                            tvTotal.setText(Integer.toString(integer) + " Ks");
                        }
                    });
                } else {
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    ibtnRight.setEnabled(false);
                }
            }
        });

        ImageButton ibtnLeft = (ImageButton) v.findViewById(R.id.ibtn_left);
        ibtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                tvDate.setText(day + "-" + (month + 1) + "-" + year);
                String dateString = day + "-" + (month + 1) + "-" + year;

//                getAdapterData(dateString);

                mCashInViewModel.dateCashIn(dateString).observe(getActivity(), new Observer<List<CashIn>>() {
                    @Override
                    public void onChanged(@Nullable List<CashIn> cashIns) {
                        adapter.setCashIns(cashIns);
                    }
                });

                mCashInViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer amountTotal) {
                        tvTotal.setText(Integer.toString(amountTotal) + " Ks");
                    }
                });

                ibtnRight.setEnabled(true);
            }
        });

        getAdapterData(dateString);

        return v;
    }

    private void getAdapterData(String dateString) {
        mCashInViewModel.getmAllCashIn().observe(this, new Observer<List<CashIn>>() {
            @Override
            public void onChanged(@Nullable List<CashIn> cashIns) {
                adapter.setCashIns(cashIns);
            }
        });

        mCashInViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                tvTotal.setText(Integer.toString(integer) + " Ks");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_IN_CATEGORY && resultCode == RESULT_OK) {
            CashIn cashIn = new CashIn(0, data.getStringExtra(ADD_CATEGORY_REPLY_DATE), data.getStringExtra(ADD_CATEGORY_REPLY_CATEGORY), data.getStringExtra(ADD_CATEGORY_REPLY_AMOUNT));
            mCashInViewModel.insertCashIn(cashIn);
            Toast.makeText(getContext(), "Data Save Fail", Toast.LENGTH_SHORT).show();
        } else if (requestCode == NEW_IN_CATEGORY && resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(), "Data Save Fail", Toast.LENGTH_SHORT).show();
        }
    }
}
