package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WiseCompanionRepository {
    private WiseWordsDao wiseWordsDao;
    private CompanionModelDao companionDao;
    private UserDao userDao;
    private LiveData<List<WiseWords>> mAllWiseWords;
    private LiveData<List<CompanionModel>> mCompanion;


    WiseCompanionRepository(Application application) {
        WiseCompanionRoomDatabase db = WiseCompanionRoomDatabase.getDatabase(application);
        wiseWordsDao = db.wiseWordsDao();
        companionDao = db.companionModelDao();
        userDao = db.userDao();
        mAllWiseWords = wiseWordsDao.getAllWiseWords();
        mCompanion = companionDao.getAllCompanion();
    }

    // user
    public void newUser(UserModel userModel){
        new insertUserAsync(userDao).execute(userModel);
    }

    private static class insertUserAsync extends AsyncTask<UserModel, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertUserAsync(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    // companions
    LiveData<List<CompanionModel>> getAllCompanion() {
        return mCompanion;
    }

    List<CompanionModel> getAllCompanionModel() {
        return companionDao.getAllCompanionModel();
    }

    public void newCompanion(CompanionModel companionModel){
        new insertCompanionAsync(companionDao).execute(companionModel);
    }

    public void setHungerLevel(CompanionModel companion, long hungerLvl){
        new updateCompanionAsync(companionDao, companion).execute(hungerLvl);
    }

    public void setAffectionLevel(CompanionModel companion, long affectionLvl){
        new updateCompanionAffectionAsync(companionDao, companion).execute(affectionLvl);
    }

    public void setAge(CompanionModel companion, long age){
        new updateCompanionAgeAsync(companionDao, companion).execute(age);
    }

    private static class insertCompanionAsync extends AsyncTask<CompanionModel, Void, Void> {

        private CompanionModelDao mAsyncTaskDao;

        insertCompanionAsync(CompanionModelDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CompanionModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateCompanionAsync extends AsyncTask<Long, Void, Void> {
        private CompanionModel mCompanionModel;
        private CompanionModelDao mAsyncTaskDao;

        updateCompanionAsync(CompanionModelDao dao, CompanionModel companionModel) {
            mAsyncTaskDao = dao;
            mCompanionModel = companionModel;
        }

        @Override
        protected Void doInBackground(final Long... params) {
            int companionID = mCompanionModel.getId();
            mAsyncTaskDao.setHungerLevel(companionID, params[0]);
            return null;
        }
    }

    private static class updateCompanionAffectionAsync extends AsyncTask<Long, Void, Void> {
        private CompanionModel mCompanionModel;
        private CompanionModelDao mAsyncTaskDao;

        updateCompanionAffectionAsync(CompanionModelDao dao, CompanionModel companionModel) {
            mAsyncTaskDao = dao;
            mCompanionModel = companionModel;
        }

        @Override
        protected Void doInBackground(final Long... params) {
            int companionID = mCompanionModel.getId();
            mAsyncTaskDao.setAffectionLevel(companionID, params[0]);
            return null;
        }
    }

    private static class updateCompanionAgeAsync extends AsyncTask<Long, Void, Void> {
        private CompanionModel mCompanionModel;
        private CompanionModelDao mAsyncTaskDao;

        updateCompanionAgeAsync(CompanionModelDao dao, CompanionModel companionModel) {
            mAsyncTaskDao = dao;
            mCompanionModel = companionModel;
        }

        @Override
        protected Void doInBackground(final Long... params) {
            int companionID = mCompanionModel.getId();
            mAsyncTaskDao.setAge(companionID, params[0]);
            return null;
        }
    }

    // wise words

    LiveData<List<WiseWords>> getAllWiseWords() {
        return mAllWiseWords;
    }

    public void insertWiseWord (WiseWords wiseWords) {
        new insertWiseWordAsync(wiseWordsDao).execute(wiseWords);
    }

    public void saidWiseWord (int wiseWordID) {
        new updateWiseWordsAsync(wiseWordsDao).execute(wiseWordID);
    }

    private static class insertWiseWordAsync extends AsyncTask<WiseWords, Void, Void> {

        private WiseWordsDao mAsyncTaskDao;

        insertWiseWordAsync(WiseWordsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WiseWords... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateWiseWordsAsync extends AsyncTask<Integer, Void, Void> {

        private WiseWordsDao mAsyncTaskDao;

        updateWiseWordsAsync(WiseWordsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.has_been_said(params[0]);
            return null;
        }
    }
}
