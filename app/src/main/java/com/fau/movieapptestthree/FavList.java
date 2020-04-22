package com.fau.movieapptestthree;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName="favoritelist")
public class FavList {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "fposter")
    private String posterURL;

    @ColumnInfo(name = "favname")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}

