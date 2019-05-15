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

import myanmar.gic.com.myinoutnote.Adapters.CashOutAdapter;
import myanmar.gic.com.myinoutnote.Models.CashOut;
import myanmar.gic.com.myinoutnote.ViewModels.CashOutViewModel;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static myanmar.gic.com.myinoutnote.AddCashOutActivity.ADD_CATEGORY_OUT_REPLY_AMOUNT;
import static myanmar.gic.com.myinoutnote.AddCashOutActivity.ADD_CATEGORY_OUT_REPLY_CATEGORY;
import static myanmar.gic.com.myinoutnote.AddCashOutActivity.ADD_CATEGORY_OUT_REPLY_DATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CashOutFragment extends Fragment {
    private CashOutViewModel mCashOutViewModel;
    private int NEW_OUT_CATEGORY = 1;
    private Calendar cal;

    CashOutAdapter adapter;

    TextView tvTotal;

    public CashOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cash_out, container, false);

        cal = Calendar.getInstance();

        cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_cash_out);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goIntent = new Intent(getActivity(), AddCashOutActivity.class);
                startActivityForResult(goIntent, NEW_OUT_CATEGORY);
            }
        });

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rcv_cash_out);

        adapter = new CashOutAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCashOutViewModel = ViewModelProviders.of(this).get(CashOutViewModel.class);

        final TextView tvDate = (TextView) v.findViewById(R.id.tv_date);
        tvTotal = (TextView) v.findViewById(R.id.tv_total);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        tvDate.setText(day + "-" + (month + 1) + "-" + year);
        String dateString = day + "-" + (month + 1) + "-" + year;

        final ImageButton ibtnRight = (ImageButton) v.findViewById(R.id.ibtn_right);
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
                    String dateString = day + "-" + (month + 1) + "-" + year;

                    tvDate.setText(day + "-" + (month + 1) + "-" + year);

//                getAdapterData(dateString);

                    mCashOutViewModel.dateCashOut(dateString).observe(getActivity(), new Observer<List<CashOut>>() {
                        @Override
                        public void onChanged(@Nullable List<CashOut> cashOuts) {
                            adapter.setCashOut(cashOuts);
                        }
                    });

                    mCashOutViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer amountTotal) {
                            tvTotal.setText(Integer.toString(amountTotal) + " Ks");
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
                String dateString = day + "-" + (month + 1) + "-" + year;

                tvDate.setText(day + "-" + (month + 1) + "-" + year);

//                getAdapterData(dateString);

                mCashOutViewModel.dateCashOut(dateString).observe(getActivity(), new Observer<List<CashOut>>() {
                    @Override
                    public void onChanged(@Nullable List<CashOut> cashOuts) {
                        adapter.setCashOut(cashOuts);
                    }
                });

                mCashOutViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer amountTotal) {
                        tvTotal.setText(Integer.toString(amountTotal) + " Ks");
                    }
                });
            }
        });

        getAdapterData(dateString);

        return v;
    }

    private void getAdapterData(String dateString) {
        mCashOutViewModel.getmAllCashOut().observe(this, new Observer<List<CashOut>>() {
            @Override
            public void onChanged(@Nullable List<CashOut> cashOuts) {
                adapter.setCashOut(cashOuts);
            }
        });

        mCashOutViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                tvTotal.setText(Integer.toString(integer) + " Ks");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_OUT_CATEGORY && resultCode == RESULT_OK) {
            CashOut cashOut = new CashOut(0, data.getStringExtra(ADD_CATEGORY_OUT_REPLY_DATE), data.getStringExtra(ADD_CATEGORY_OUT_REPLY_CATEGORY), data.getStringExtra(ADD_CATEGORY_OUT_REPLY_AMOUNT));
            mCashOutViewModel.insertCashOut(cashOut);

            Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
        } else if (requestCode == NEW_OUT_CATEGORY && resultCode == RESULT_CANCELED) {
            Toast.makeText(getContext(), "Data Save Fail", Toast.LENGTH_SHORT).show();
        }
    }
}
