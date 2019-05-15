package myanmar.gic.com.myinoutnote.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tbl_cash_out")
public class CashOut {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "amount")
    private String mAmount;

    @ColumnInfo(name = "cashoutctg")
    private String mCashOutCtg;

    public CashOut(int id, @NonNull String date, String cashOutCtg, String amount) {
        this.mId = id;
        this.mDate = date;
        this.mAmount = amount;
        this.mCashOutCtg = cashOutCtg;
    }

    @NonNull
    public int getId() {
        return mId;
    }

    public void setId(@NonNull int id) {
        this.mId = id;
    }

    @NonNull
    public String getDate() {
        return mDate;
    }

    public String getAmount() {
        return mAmount;
    }

    public String getCashOutCtg() {
        return mCashOutCtg;
    }
}
