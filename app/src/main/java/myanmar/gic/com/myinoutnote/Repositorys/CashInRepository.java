package myanmar.gic.com.myinoutnote.Repositorys;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import myanmar.gic.com.myinoutnote.Daos.CashInDao;
import myanmar.gic.com.myinoutnote.Database.InOutNoteDatabase;
import myanmar.gic.com.myinoutnote.Models.CashIn;

public class CashInRepository {
    private CashInDao mCashInDao;
    private LiveData<List<CashIn>> mAllCashIn;
    private LiveData<Integer> mAmountTotal;

    public CashInRepository(Application application, String date) {
        InOutNoteDatabase db = InOutNoteDatabase.getInstance(application);
        mCashInDao = db.cashInDao();
        mAllCashIn = mCashInDao.getDateCashIn(date);
        mAmountTotal = mCashInDao.getDayAmountTotal(date);
    }

    public LiveData<List<CashIn>> getAllCashIn() {
        return mAllCashIn;
    }

    public LiveData<List<CashIn>> getDashboardDate() {
        return mCashInDao.getAllCashIn();
    }

    public LiveData<List<CashIn>> dateCashIn(String date) {
        mAllCashIn = mCashInDao.getDateCashIn(date);
        return mAllCashIn;
    }

    public LiveData<Integer> getAmountTotal(String date) {
        mAmountTotal = mCashInDao.getDayAmountTotal(date);
        return mAmountTotal;
    }

    public void deleteCashIn(int id) {
        mCashInDao.delete(id);
    }

    public void insertCashIn(CashIn cashIn) {
        new InsertCashIn(mCashInDao).execute(cashIn);
    }

    private class InsertCashIn extends AsyncTask<CashIn, Void, Void> {
        private CashInDao mDao;

        InsertCashIn(CashInDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(CashIn... cashIns) {
            mDao.insert(cashIns[0]);
            return null;
        }
    }

    public void updateCashOut(CashIn cashIn) {
        new UpdateCashIn(mCashInDao).execute(cashIn);
    }

    private class UpdateCashIn extends AsyncTask<CashIn, Void, Void> {
        private CashInDao mDao;

        UpdateCashIn(CashInDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(CashIn... cashIns) {
            mDao.update(cashIns[0].getDate(), cashIns[0].getCashInCtg(), cashIns[0].getAmount(), cashIns[0].getId());
            return null;
        }
    }
}
