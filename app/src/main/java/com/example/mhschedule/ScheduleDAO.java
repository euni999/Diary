package com.example.mhschedule;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScheduleDAO {
    @Query("SELECT * FROM schedule")
    LiveData<List<ScheduleEntity>> getAll();

    @Insert
    void insert(ScheduleEntity... daily);

    @Update
    void update(ScheduleEntity daily);

    @Delete
    void delete(ScheduleEntity daily);

    @Query("DELETE FROM schedule")
    void deleteAll();

}
