package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CompanionModelDao {
    @Insert
    void insert(CompanionModel companion);

    @Query("UPDATE companion SET last_seen=:time WHERE id=:wiseWordsID")
    void last_seen(int wiseWordsID, String time);

    @Query("SELECT * FROM companion")
    LiveData<List<CompanionModel>> getAllCompanion();

    @Query("SELECT * FROM companion")
    List<CompanionModel> getAllCompanionModel();

    @Query("UPDATE companion SET hungerLevel=:hungerLvl WHERE id=:companionID")
    void setHungerLevel(int companionID, long hungerLvl);

    @Query("UPDATE companion SET affectionLevel=:affectionLvl WHERE id=:companionID")
    void setAffectionLevel(int companionID, long affectionLvl);

    @Query("UPDATE companion SET age=:age WHERE id=:companionID")
    void setAge(int companionID, long age);

    @Query("DELETE FROM wise_words")
    void deleteAll();
}
