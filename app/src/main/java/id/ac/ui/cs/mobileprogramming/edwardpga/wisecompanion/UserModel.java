package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String presence_time;

    public UserModel(@NonNull String presence_time){
        this.presence_time = presence_time;
    }

    @NonNull
    public String getPresence_time() {
        return presence_time;
    }

    public void setPresence_time(@NonNull String presence_time) {
        this.presence_time = presence_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
