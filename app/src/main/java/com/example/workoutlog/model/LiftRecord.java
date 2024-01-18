package com.example.workoutlog.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lift_record_table")
public class LiftRecord {

    @PrimaryKey(autoGenerate = true)
    public int recordID;

    @ColumnInfo(name = "date_record")
    public String dateRecord;

    @ColumnInfo(name = "name_record")
    public String nameRecord;

    @ColumnInfo(name = "weight_record")
    public double weight;

    @ColumnInfo(name = "reps_record")
    public int reps;

    public LiftRecord(){

    }
}
