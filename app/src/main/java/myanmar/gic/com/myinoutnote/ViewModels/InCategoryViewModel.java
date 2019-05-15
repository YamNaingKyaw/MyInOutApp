package myanmar.gic.com.myinoutnote.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.InCategory;
import myanmar.gic.com.myinoutnote.Repositorys.InCategoryRepository;

public class InCategoryViewModel extends AndroidViewModel {
    private InCategoryRepository mInCategoryReposity;
    private LiveData<List<InCategory>> mAllInCategory;

    public InCategoryViewModel(@NonNull Application application) {
        super(application);
        mInCategoryReposity = new InCategoryRepository(application);
        mAllInCategory = mInCategoryReposity.getAllInCategory();
    }

    public LiveData<List<InCategory>> getmAllInCategory() {
        return mAllInCategory;
    }

    public void insertInCategory(InCategory inCategory) {
        mInCategoryReposity.insertInCategory(inCategory);
    }
}
