package myanmar.gic.com.myinoutnote.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.OutCategory;

@Dao
public interface OutCategoryDao {
    @Query("select * from tbl_out order by outcategory asc")
    LiveData<List<OutCategory>> getAllOutCategory();

    @Insert
    void insertOutCategory(OutCategory outCategory);

    @Delete
    void deleteOutCategory(OutCategory outCategory);
}
