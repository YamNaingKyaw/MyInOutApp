package myanmar.gic.com.myinoutnote.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

import myanmar.gic.com.myinoutnote.Models.CashOut;
import myanmar.gic.com.myinoutnote.Repositorys.CashOutRepository;

public class CashOutViewModel extends AndroidViewModel {
    private CashOutRepository mCashOutRepository;
    private LiveData<List<CashOut>> mAllCashOut;

    Calendar cal = Calendar.getInstance();
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH);
    int year = cal.get(Calendar.YEAR);

    String date = day + "-" + (month + 1) + "-" + year;

    public CashOutViewModel(@NonNull Application application) {
        super(application);
        mCashOutRepository = new CashOutRepository(application, date);
        mAllCashOut = mCashOutRepository.getAllCashOut();
    }

    public LiveData<List<CashOut>> getmAllCashOut() {
        return mAllCashOut;
    }

    public LiveData<List<CashOut>> dateCashOut(String date) {
        return mCashOutRepository.dateCashOut(date);
    }

    public LiveData<List<CashOut>> getDashboardData() {
        return mCashOutRepository.getDashboardData();
    }

    public LiveData<Integer> getDateAmountTotal(String date) {
        return mCashOutRepository.getAmountTotal(date);
    }

    public void deleteCashOut(int id) {
        mCashOutRepository.deleteCashOut(id);
    }

    public void insertCashOut(CashOut cashOut) {
        mCashOutRepository.insertCashOut(cashOut);
    }

    public void updateCashOut(CashOut cashOut) {
        mCashOutRepository.updateCashOut(cashOut);
    }
}
