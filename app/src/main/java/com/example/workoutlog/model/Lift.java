package com.example.workoutlog.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lift_list_table")
public class Lift {

    @PrimaryKey(autoGenerate = true)
    public int liftID;

    @ColumnInfo(name = "lift_name")
    public String liftName;

    @ColumnInfo(name = "num_sets")
    public int sets;

    @ColumnInfo(name = "goal_reps")
    public int goalReps;

    public Lift(){

    }
}
