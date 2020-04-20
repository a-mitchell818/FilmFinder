package com.fau.movieapptestthree;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavDao {
    @Insert
    public void addData(FavList favoriteList);

    @Query("select * from favoritelist")
    public List<FavList> getFavoriteData();
    // List<FavList> getAll();
    //  public List&lt;FavoriteList> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    public Integer isFavorite(Integer id);

    @Delete
    public void delete(FavList favoriteList);
}