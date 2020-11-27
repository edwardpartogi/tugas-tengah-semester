package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "companion")
public class CompanionModel {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String name;

    @NonNull
    private long age=0;

    @NonNull
    private long hungerLevel;

    @NonNull
    private long affectionLevel;

    private String last_seen="F";

    public CompanionModel(@NonNull String name, @NonNull long hungerLevel, @NonNull long affectionLevel){
        this.name = name;
        this.hungerLevel = hungerLevel;
        this.affectionLevel = affectionLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public long getHungerLevel() {
        return hungerLevel;
    }

    public long getAffectionLevel() {
        return affectionLevel;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }
}
