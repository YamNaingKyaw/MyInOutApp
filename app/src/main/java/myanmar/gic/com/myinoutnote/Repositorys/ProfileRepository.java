package myanmar.gic.com.myinoutnote.Repositorys;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import myanmar.gic.com.myinoutnote.Daos.ProfileDao;
import myanmar.gic.com.myinoutnote.Database.InOutNoteDatabase;
import myanmar.gic.com.myinoutnote.Models.Profile;

public class ProfileRepository {
    private ProfileDao mProfileDao;
    private LiveData<List<Profile>> mAllProfile;

    public ProfileRepository(Application application) {
        InOutNoteDatabase db = InOutNoteDatabase.getInstance(application);
        mProfileDao = db.profileDao();
        mAllProfile = mProfileDao.getAllProfile();
    }

    public LiveData<List<Profile>> getAllProfile() {
        return mAllProfile;
    }

    public void insertProfile(Profile profile) {
        new InsertProfile(mProfileDao).execute(profile);
    }

    private class InsertProfile extends AsyncTask<Profile, Void, Void> {
        private ProfileDao mDao;

        InsertProfile(ProfileDao dao) {
            this.mDao = dao;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            mDao.insertProfile(profiles[0]);
            return null;
        }
    }

    public void updateProfile(Profile profile, String oldName) {
        new UpdateProfile(mProfileDao, oldName).execute(profile);
    }

    private class UpdateProfile extends AsyncTask<Profile, Void, Void> {

        private ProfileDao mDao;
        private String mOldName;

        UpdateProfile(ProfileDao dao, String oldName) {
            this.mDao = dao;
            this.mOldName = oldName;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            mDao.updateFullProfile(profiles[0].getName(), profiles[0].getNrc(), profiles[0].getGender(), profiles[0].getAddress(), profiles[0].getPhone(), profiles[0].getImage(), mOldName);
            return null;
        }
    }
}
