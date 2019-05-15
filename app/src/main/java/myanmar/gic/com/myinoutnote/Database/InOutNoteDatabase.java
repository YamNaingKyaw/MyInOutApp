package myanmar.gic.com.myinoutnote.Database;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import myanmar.gic.com.myinoutnote.Daos.CashInDao;
import myanmar.gic.com.myinoutnote.Daos.CashOutDao;
import myanmar.gic.com.myinoutnote.Daos.InCategoryDao;
import myanmar.gic.com.myinoutnote.Daos.OutCategoryDao;
import myanmar.gic.com.myinoutnote.Daos.ProfileDao;
import myanmar.gic.com.myinoutnote.Models.CashIn;
import myanmar.gic.com.myinoutnote.Models.CashOut;
import myanmar.gic.com.myinoutnote.Models.InCategory;
import myanmar.gic.com.myinoutnote.Models.OutCategory;
import myanmar.gic.com.myinoutnote.Models.Profile;

@Database(entities = {Profile.class, CashIn.class, CashOut.class, InCategory.class, OutCategory.class}, version = 1)
public abstract class InOutNoteDatabase extends RoomDatabase {
    public abstract ProfileDao profileDao();

    public abstract CashInDao cashInDao();

    public abstract CashOutDao cashOutDao();

    public abstract InCategoryDao inCategoryDao();

    public abstract OutCategoryDao outCategoryDao();

    public static volatile InOutNoteDatabase INSTANCE;

    public static InOutNoteDatabase getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (InOutNoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(application.getApplicationContext(), InOutNoteDatabase.class, "inoutdatabase")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };
}
