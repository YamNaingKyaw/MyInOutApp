package myanmar.gic.com.myinoutnote.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import myanmar.gic.com.myinoutnote.Models.Profile;
import myanmar.gic.com.myinoutnote.Repositorys.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository mProfileRepository;
    private LiveData<List<Profile>> mAllProfile;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mProfileRepository = new ProfileRepository(application);
        mAllProfile = mProfileRepository.getAllProfile();
    }

    public LiveData<List<Profile>> getAllProfile() {
        return mAllProfile;
    }

    public void insertProfile(Profile profile) {
        mProfileRepository.insertProfile(profile);
    }

    public void updateProfile(Profile profile, String oldName) {
        mProfileRepository.updateProfile(profile, oldName);
    }
}
