package com.example.workoutlog.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LiftDAO {

    @Query("SELECT * FROM lift_list_table")
    List<Lift> getAllLifts();

    @Insert
    void insertLift(Lift... lifts);

    @Update
    void updateLift(Lift liftObj);

    @Delete
    void delete(Lift liftObj);
}
