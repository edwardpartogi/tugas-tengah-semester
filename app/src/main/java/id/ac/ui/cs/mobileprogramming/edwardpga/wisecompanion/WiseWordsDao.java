package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WiseWordsDao {
    @Insert
    void insert(WiseWords wiseWords);

    @Query("SELECT * FROM wise_words")
    LiveData<List<WiseWords>> getAllWiseWords();

    @Query("DELETE FROM wise_words")
    void deleteAll();

    @Query("UPDATE wise_words SET is_said=1 WHERE id=:wiseWordsID")
    void has_been_said(int wiseWordsID);
}
