package myanmar.gic.com.myinoutnote.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

import myanmar.gic.com.myinoutnote.Models.CashIn;
import myanmar.gic.com.myinoutnote.Repositorys.CashInRepository;

public class CashInViewModel extends AndroidViewModel {
    private CashInRepository mCashInRepository;
    private LiveData<List<CashIn>> mAllCashIn;

    Calendar cal = Calendar.getInstance();
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH);
    int year = cal.get(Calendar.YEAR);

    String date = day + "-" + (month + 1) + "-" + year;

    public CashInViewModel(@NonNull Application application) {
        super(application);
        mCashInRepository = new CashInRepository(application, date);
        mAllCashIn = mCashInRepository.getAllCashIn();
    }

    public LiveData<List<CashIn>> getmAllCashIn() {
        return mAllCashIn;
    }

    public LiveData<List<CashIn>> dateCashIn(String date) {
        return mCashInRepository.dateCashIn(date);
    }

    public LiveData<Integer> getDateAmountTotal(String date) {
        return mCashInRepository.getAmountTotal(date);
    }

    public LiveData<List<CashIn>> getDashboardData() {
        return mCashInRepository.getDashboardDate();
    }

    public void deleteCashIn(int id) {
        mCashInRepository.deleteCashIn(id);
    }

    public void insertCashIn(CashIn cashIn) {
        mCashInRepository.insertCashIn(cashIn);
    }

    public void updateCashIn(CashIn cashIn) {
        mCashInRepository.updateCashOut(cashIn);
    }
}
