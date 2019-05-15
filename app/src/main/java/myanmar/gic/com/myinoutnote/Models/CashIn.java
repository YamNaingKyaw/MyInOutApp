package myanmar.gic.com.myinoutnote.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tbl_cash_in")
public class CashIn {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "amount")
    private String mAmount;

    @ColumnInfo(name = "cashinctg")
    private String mCashInCtg;

    public CashIn(int id, @NonNull String date, String cashInCtg, String amount) {
        this.mId = id;
        this.mDate = date;
        this.mAmount = amount;
        this.mCashInCtg = cashInCtg;
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

    public String getCashInCtg() {
        return mCashInCtg;
    }
}
