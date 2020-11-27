package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface UserDao {
    @Insert
    void insert(UserModel companion);
}
