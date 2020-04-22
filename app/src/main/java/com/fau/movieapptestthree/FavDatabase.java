package com.fau.movieapptestthree;
import androidx.room.Database;
import androidx.room.RoomDatabase;

//@Database(entities={FavList.class},version = 1, exportSchema = false)
@Database(entities={FavList.class},version = 2, exportSchema = false)
public abstract class FavDatabase extends RoomDatabase {

    public abstract FavDao favoriteDao();
}