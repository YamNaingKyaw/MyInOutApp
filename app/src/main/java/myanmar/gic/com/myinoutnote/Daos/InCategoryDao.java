package myanmar.gic.com.myinoutnote.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.InCategory;

@Dao
public interface InCategoryDao {
    @Query("select * from tbl_in order by incategory asc")
    LiveData<List<InCategory>> getAllInCategory();

    @Insert
    void insertInCategory(InCategory inCategory);

    @Delete
    void deleteInCategory(InCategory inCategory);
}
