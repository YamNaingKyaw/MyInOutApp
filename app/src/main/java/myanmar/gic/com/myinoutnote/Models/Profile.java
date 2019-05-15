package myanmar.gic.com.myinoutnote.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tbl_profile")
public class Profile {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "nrc")
    private String mNrc;

    @ColumnInfo(name = "gendre")
    private String mGender;

    @ColumnInfo(name = "address")
    private String mAddress;

    @ColumnInfo(name = "phone")
    private String mPhone;

    @ColumnInfo(name = "img")
    private byte[] mImage;

    public Profile(@NonNull String mName, String mNrc, String mGender, String mAddress, String mPhone, byte[] mImage) {
        this.mName = mName;
        this.mNrc = mNrc;
        this.mGender = mGender;
        this.mAddress = mAddress;
        this.mPhone = mPhone;
        this.mImage = mImage;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public String getNrc() {
        return mNrc;
    }

    public String getGender() {
        return mGender;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getPhone() {
        return mPhone;
    }

    public byte[] getImage() {
        return mImage;
    }
}
