package com.example.ftbb.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {user.class,game.class,team.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public abstract UserDao userDao();
    public abstract TeamDao teamDao();
    public abstract GameDao gameDao();

    public static AppDataBase getAppDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "ftbb")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
