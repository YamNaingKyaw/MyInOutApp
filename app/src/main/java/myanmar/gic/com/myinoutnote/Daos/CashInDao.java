package myanmar.gic.com.myinoutnote.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.CashIn;

@Dao
public interface CashInDao {
    @Query("select * from tbl_cash_in order by date asc")
    LiveData<List<CashIn>> getAllCashIn();

    @Query("select * from tbl_cash_in where date=:date order by date asc")
    LiveData<List<CashIn>> getDateCashIn(String date);

    @Query("select ifnull(sum(amount),0) from tbl_cash_in where date=:date order by date asc")
    LiveData<Integer> getDayAmountTotal(String date);

    @Insert
    void insert(CashIn cashIn);

    @Query("update tbl_cash_in set date=:date,cashinctg=:inCategory,amount=:amount where id=:id")
    void update(String date, String inCategory, String amount, int id);

    @Query("delete from tbl_cash_in where id=:id")
    void delete(int id);
}
