package myanmar.gic.com.myinoutnote.Repositorys;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import myanmar.gic.com.myinoutnote.Daos.InCategoryDao;
import myanmar.gic.com.myinoutnote.Database.InOutNoteDatabase;
import myanmar.gic.com.myinoutnote.Models.InCategory;

public class InCategoryRepository {
    private InCategoryDao mInCategoryDao;
    private LiveData<List<InCategory>> mAllInCategory;

    public InCategoryRepository(Application application) {
        InOutNoteDatabase db = InOutNoteDatabase.getInstance(application);
        mInCategoryDao = db.inCategoryDao();
        mAllInCategory = mInCategoryDao.getAllInCategory();
    }

    public LiveData<List<InCategory>> getAllInCategory() {
        return mAllInCategory;
    }

    public void insertInCategory(InCategory inCategory) {
        new InsertInCategory(mInCategoryDao).execute(inCategory);
    }

    private class InsertInCategory extends AsyncTask<InCategory, Void, Void> {
        private InCategoryDao mDao;

        public InsertInCategory(InCategoryDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(InCategory... inCategories) {
            mDao.insertInCategory(inCategories[0]);
            return null;
        }
    }
}
