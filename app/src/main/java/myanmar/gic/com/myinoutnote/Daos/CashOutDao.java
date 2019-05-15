package myanmar.gic.com.myinoutnote.Daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.CashOut;

@Dao
public interface CashOutDao {
    @Query("select id,date,sum(amount) as amount,cashoutctg from tbl_cash_out group by date")
    LiveData<List<CashOut>> getAllCashOut();

    @Query("select * from tbl_cash_out where date=:date order by date asc")
    LiveData<List<CashOut>> getDateCashOut(String date);

    @Query("select ifnull(sum(amount),0) from tbl_cash_out where date=:date order by date asc")
    LiveData<Integer> getDayAmountTotal(String date);

    @Insert
    void insert(CashOut cashOut);

    @Query("update tbl_cash_out set date=:date,cashoutctg=:outCategory,amount=:amount where id=:id")
    void update(String date, String outCategory, String amount, int id);

    @Query("delete from tbl_cash_out where id=:id")
    void delete(int id);
}
