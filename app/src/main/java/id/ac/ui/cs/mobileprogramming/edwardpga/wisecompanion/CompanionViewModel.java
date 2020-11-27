package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CompanionViewModel extends AndroidViewModel {
    private WiseCompanionRepository mRepository;
    private LiveData<List<CompanionModel>> mCompanion;

    public CompanionViewModel (Application application) {
        super(application);
        mRepository = new WiseCompanionRepository(application);
        mCompanion = mRepository.getAllCompanion();
    }

    LiveData<List<CompanionModel>> getAllCompanion() { return mCompanion; }

    List<CompanionModel> getAllCompanionModel() { return mRepository.getAllCompanionModel(); }

    public void newCompanion(CompanionModel companion){
        mRepository.newCompanion(companion);
    }

    public void setHungerLevel(CompanionModel companion, long hungerLevel) {
        mRepository.setHungerLevel(companion, hungerLevel);
    }

    public void setAffectionLevel(CompanionModel companion, long affectionLvl) {
        mRepository.setAffectionLevel(companion, affectionLvl);
    }

    public void setAge(CompanionModel companion, long age){
        mRepository.setAge(companion, age);
    }
}
