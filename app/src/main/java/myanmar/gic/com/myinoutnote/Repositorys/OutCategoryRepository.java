package myanmar.gic.com.myinoutnote.Repositorys;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import myanmar.gic.com.myinoutnote.Daos.OutCategoryDao;
import myanmar.gic.com.myinoutnote.Database.InOutNoteDatabase;
import myanmar.gic.com.myinoutnote.Models.OutCategory;

public class OutCategoryRepository {
    private OutCategoryDao mOutCategoryDao;
    private LiveData<List<OutCategory>> mAllOutCategory;

    public OutCategoryRepository(Application application) {
        InOutNoteDatabase db = InOutNoteDatabase.getInstance(application);
        mOutCategoryDao = db.outCategoryDao();
        mAllOutCategory = mOutCategoryDao.getAllOutCategory();
    }

    public LiveData<List<OutCategory>> getAllOutCategory() {
        return mAllOutCategory;
    }

    public void insertOutCategory(OutCategory outCategory) {
        new InsertOutCategory(mOutCategoryDao).execute(outCategory);
    }

    public class InsertOutCategory extends AsyncTask<OutCategory, Void, Void> {
        private OutCategoryDao mDao;

        public InsertOutCategory(OutCategoryDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(OutCategory... outCategories) {
            mDao.insertOutCategory(outCategories[0]);
            return null;
        }
    }
}
