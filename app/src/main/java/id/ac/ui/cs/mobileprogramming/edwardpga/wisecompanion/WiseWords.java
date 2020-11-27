package id.ac.ui.cs.mobileprogramming.edwardpga.wisecompanion;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wise_words")
public class WiseWords {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String wiseWord_en;

    @NonNull
    private String wiseWord_id;

    @NonNull
    private String wiseWord_cat;

    @NonNull
    private String wiseWord_dog;

    private boolean is_said = false;

    public WiseWords(@NonNull String wiseWord_en, @NonNull String wiseWord_id, @NonNull String wiseWord_cat, @NonNull String wiseWord_dog){
        this.wiseWord_en = wiseWord_en;
        this.wiseWord_id = wiseWord_id;
        this.wiseWord_cat = wiseWord_cat;
        this.wiseWord_dog = wiseWord_dog;
    }

    public void has_been_said(){
        this.is_said = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getWiseWord_en() {
        return wiseWord_en;
    }

    public void setWiseWord_en(@NonNull String wiseWord_en) {
        this.wiseWord_en = wiseWord_en;
    }

    @NonNull
    public String getWiseWord_id() {
        return wiseWord_id;
    }

    public void setWiseWord_id(@NonNull String wiseWord_id) {
        this.wiseWord_id = wiseWord_id;
    }

    @NonNull
    public String getWiseWord_cat() {
        return wiseWord_cat;
    }

    public void setWiseWord_cat(@NonNull String wiseWord_cat) {
        this.wiseWord_cat = wiseWord_cat;
    }

    @NonNull
    public String getWiseWord_dog() {
        return wiseWord_dog;
    }

    public void setWiseWord_dog(@NonNull String wiseWord_dog) {
        this.wiseWord_dog = wiseWord_dog;
    }

    public boolean getIs_said(){
        return is_said;
    }

    public void setIs_said(boolean is_said) {
        this.is_said = is_said;
    }
}
