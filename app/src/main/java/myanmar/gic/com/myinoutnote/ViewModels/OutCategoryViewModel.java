package myanmar.gic.com.myinoutnote.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.OutCategory;
import myanmar.gic.com.myinoutnote.Repositorys.OutCategoryRepository;

public class OutCategoryViewModel extends AndroidViewModel {
    private OutCategoryRepository mOutCategoryRepository;
    private LiveData<List<OutCategory>> mAllOutCategory;

    public OutCategoryViewModel(@NonNull Application application) {
        super(application);
        mOutCategoryRepository = new OutCategoryRepository(application);
        mAllOutCategory = mOutCategoryRepository.getAllOutCategory();
    }

    public LiveData<List<OutCategory>> getmAllOutCategory() {
        return mAllOutCategory;
    }

    public void insertOutCategory(OutCategory outCategory) {
        mOutCategoryRepository.insertOutCategory(outCategory);
    }
}
