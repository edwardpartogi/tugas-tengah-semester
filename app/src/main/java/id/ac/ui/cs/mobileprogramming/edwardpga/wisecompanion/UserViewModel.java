package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private WiseCompanionRepository mRepository;
    private LiveData<List<WiseWords>> mAllWiseWords;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new WiseCompanionRepository(application);
        mAllWiseWords = mRepository.getAllWiseWords();
    }

    LiveData<List<WiseWords>> getAllWiseWords() { return mAllWiseWords; }

    public void insert(WiseWords word) { mRepository.insertWiseWord(word); }
}

