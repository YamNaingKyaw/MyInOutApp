package myanmar.gic.com.myinoutnote.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tbl_out")
public class OutCategory {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "outcategory")
    private String mOutCtg;

    public OutCategory(@NonNull String outCtg) {
        this.mOutCtg = outCtg;
    }

    @NonNull
    public String getOutCtg() {
        return mOutCtg;
    }
}
