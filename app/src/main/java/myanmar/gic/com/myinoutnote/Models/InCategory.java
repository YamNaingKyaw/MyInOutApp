package myanmar.gic.com.myinoutnote.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tbl_in")
public class InCategory {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "incategory")
    private String mInCtg;

    public InCategory(@NonNull String inCtg) {
        this.mInCtg = inCtg;
    }

    @NonNull
    public String getInCtg() {
        return mInCtg;
    }
}
