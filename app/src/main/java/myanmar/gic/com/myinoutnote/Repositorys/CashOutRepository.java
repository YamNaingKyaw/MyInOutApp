package myanmar.gic.com.myinoutnote.Repositorys;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import myanmar.gic.com.myinoutnote.Daos.CashOutDao;
import myanmar.gic.com.myinoutnote.Database.InOutNoteDatabase;
import myanmar.gic.com.myinoutnote.Models.CashOut;

public class CashOutRepository {
    private CashOutDao mCashOutDao;
    private LiveData<List<CashOut>> mAllCashOut;
    private LiveData<Integer> mAmountTotal;

    public CashOutRepository(Application applicatoin, String date) {
        InOutNoteDatabase db = InOutNoteDatabase.getInstance(applicatoin);
        mCashOutDao = db.cashOutDao();
        mAllCashOut = mCashOutDao.getDateCashOut(date);
        mAmountTotal = mCashOutDao.getDayAmountTotal(date);
    }

    public LiveData<List<CashOut>> getAllCashOut() {
        return mAllCashOut;
    }

    public LiveData<List<CashOut>> getDashboardData() {
        return mCashOutDao.getAllCashOut();
    }

    public LiveData<List<CashOut>> dateCashOut(String date) {
        mAllCashOut = mCashOutDao.getDateCashOut(date);
        return mAllCashOut;
    }

    public LiveData<Integer> getAmountTotal(String date) {
        mAmountTotal = mCashOutDao.getDayAmountTotal(date);
        return mAmountTotal;
    }

    public void deleteCashOut(int id) {
        mCashOutDao.delete(id);
    }

    public void insertCashOut(CashOut cashOut) {
        new InsertCashOut(mCashOutDao).execute(cashOut);
    }

    public void updateCashOut(CashOut cashOut) {
        new UpdateCashOut(mCashOutDao).execute(cashOut);
    }

    private class InsertCashOut extends AsyncTask<CashOut, Void, Void> {
        private CashOutDao mDao;

        InsertCashOut(CashOutDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(CashOut... cashOuts) {
            mDao.insert(cashOuts[0]);
            return null;
        }
    }

    private class UpdateCashOut extends AsyncTask<CashOut, Void, Void> {
        private CashOutDao mDao;

        UpdateCashOut(CashOutDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(CashOut... cashOuts) {
            mDao.update(cashOuts[0].getDate(), cashOuts[0].getCashOutCtg(), cashOuts[0].getAmount(), cashOuts[0].getId());
            return null;
        }
    }
}
