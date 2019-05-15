package myanmar.gic.com.myinoutnote.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.Profile;

@Dao
public interface ProfileDao {
    @Query("select * from tbl_profile order by name asc")
    LiveData<List<Profile>> getAllProfile();

    @Insert
    void insertProfile(Profile profile);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateProfile(Profile profile);

    @Query("update tbl_profile set name=:name,nrc=:nrc,gendre=:gender,address=:address,phone=:phone,img=:img where name=:oldName")
    void updateFullProfile(String name, String nrc, String gender, String address, String phone, byte[] img, String oldName);
}
