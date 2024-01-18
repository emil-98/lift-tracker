package com.example.workoutlog.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LiftRecordDAO {

    @Query("SELECT * FROM lift_record_table")
    List<LiftRecord> getAllRecords();

    @Query("SELECT * FROM lift_record_table WHERE name_record = :liftName")
    List<LiftRecord> getRecordsByLift(String liftName);

    @Insert
    void insertRecord(LiftRecord... liftRecords);

}
