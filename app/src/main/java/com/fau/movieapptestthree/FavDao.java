package com.fau.movieapptestthree;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavDao {
    @Insert
    public void addData(FavList favoriteList);

    //NEW STUFF FOR DELETE
    //@Query("select * from favoritelist")
    //LiveData<List<FavList>> getFavoriteData();


    //PREVIOUS STUFF THAT WORKED BEFORE DELETE
    @Query("select * from favoritelist")
    public List<FavList> getFavoriteData();
    //UNCOMMENT IF FAILS


    // List<FavList> getAll();
    //  public List&lt;FavoriteList> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    public Integer isFavorite(String id);

    @Delete
    public void delete(FavList favoriteList);
}