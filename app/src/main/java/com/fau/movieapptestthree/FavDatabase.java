package com.fau.movieapptestthree;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//@Database(entities={FavList.class},version = 1, exportSchema = false)
@Database(entities={FavList.class},version = 2, exportSchema = false)
public abstract class FavDatabase extends RoomDatabase {

    public abstract FavDao favoriteDao();



    //NEW STUFF

   /* private static FavDatabase INSTANCE;

    public static FavDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                   Room.databaseBuilder(context.getApplicationContext(), FavDatabase.class, "favMovies_db")
                            .build();
        }
        return INSTANCE;
    }*/


    //NEW STUFF
}