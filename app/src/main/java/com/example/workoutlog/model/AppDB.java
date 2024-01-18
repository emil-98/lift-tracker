package com.example.workoutlog.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Lift.class, LiftRecord.class}, version = 3)
public abstract class AppDB extends RoomDatabase {

    public abstract LiftDAO liftDAO();

    public abstract LiftRecordDAO liftRecordDAO();

    private static volatile AppDB INSTANCE;

    public static AppDB getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (AppDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "LIFT_DATA").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
