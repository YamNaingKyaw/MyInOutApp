package myanmar.gic.com.myinoutnote;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import myanmar.gic.com.myinoutnote.ViewModels.CashInViewModel;
import myanmar.gic.com.myinoutnote.ViewModels.CashOutViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private CashInViewModel mCashInViewModel;
    private CashOutViewModel mCashOutViewModel;

    private Calendar cal;

    TextView tv_TotalCashIn, tv_TotalCashOut;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String dateString = day + "-" + (month + 1) + "-" + year;

        tv_TotalCashIn = (TextView) v.findViewById(R.id.tv_todaycashin);
        tv_TotalCashOut = (TextView) v.findViewById(R.id.tv_todaycashout);

        mCashInViewModel = ViewModelProviders.of(this).get(CashInViewModel.class);

        mCashOutViewModel = ViewModelProviders.of(this).get(CashOutViewModel.class);

        getTotalToday(dateString);

        return v;
    }

    private void getTotalToday(String dateString) {

        mCashInViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                tv_TotalCashIn.setText(Integer.toString(integer));
            }
        });

        mCashOutViewModel.getDateAmountTotal(dateString).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                tv_TotalCashOut.setText(Integer.toString(integer));
            }
        });
    }
}
