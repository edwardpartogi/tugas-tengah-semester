package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {CompanionModel.class, WiseWords.class}, version = 2, exportSchema = false)
public abstract class WiseCompanionRoomDatabase extends RoomDatabase {
    public abstract CompanionModelDao companionModelDao();
    public abstract WiseWordsDao wiseWordsDao();

    private static WiseCompanionRoomDatabase INSTANCE;

    public static WiseCompanionRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WiseCompanionRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WiseCompanionRoomDatabase.class, "wise_word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
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
        new RoomDatabase.Callback(){

            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new PopulateDbAsync(INSTANCE).execute();
            }
        };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CompanionModelDao mCompanionDao;
        private final WiseWordsDao mDao;

        // companion data
        String compName = "Guguk";
        long hungerLvl = 10000;
        long affectionLvl = 500;

        // wise_words data
        String[] words_en ={
            "You know i love you",
                "\"You are more special than me\". That's what you think, which surprisingly the same thinking as the one you address to ",
                "Breathe, and think 'Why am i sad?'. Yup, you know what to do now.",
                "You know, a bit of ice cream can help. Or even more than a bit?"

        };
        String[] words_in = {
            "Kamu tahu aku cinta kamu",
                "\"Kamu lebih spesial daripada aku\". Pikirmu, yang ternyata ada di pikiran dia juga",
                "Tarik nafas, dan pikirkan 'Kenapa aku sedih?'. Yes, kepikiran harus gimana sekarang, kan?",
                "Es krim satu potong sepertinya cocok buat sekarang. Atau mungkin lebih?"
        };
        String[] words_cat = {
            "Miaw Meow Miaw Love Mou",
                "'Miaw miaw meor spemow miaw meow'. Mew mew, mew miaw meow miaw.",
                "Meow meow.. mew mew 'MEW MEW MEW'. Mew, mew miaw miew miow",
                "Mew meow, meow meow meow meowmeow. Meow mew miaw?"
        };
        String[] words_dog = {
            "Bark Bark Bark Bark Bark",
                "Bark Bark Bark Bark Bark",
                "Bark Bark Bark Bark Bark",
                "Bark Bark Bark Bark Bark",
        };


        PopulateDbAsync(WiseCompanionRoomDatabase db) {
            mDao = db.wiseWordsDao();
            mCompanionDao = db.companionModelDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();
            mCompanionDao.deleteAll();

            for (int i = 0; i <= words_en.length - 1; i++) {
                WiseWords word = new WiseWords(words_en[i], words_in[i], words_cat[i], words_dog[i]);
                mDao.insert(word);
            }

            CompanionModel newCompanion = new CompanionModel(compName, hungerLvl, affectionLvl);
            mCompanionDao.insert(newCompanion);
            return null;
        }
    }
}
