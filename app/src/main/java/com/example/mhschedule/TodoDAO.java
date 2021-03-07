package com.example.mhschedule;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface TodoDAO {
    @Query("SELECT * FROM TodoEntity")
    LiveData<List<TodoEntity>>getAll();

    @Insert
    void insert(TodoEntity todo);

    @Update
    void update(TodoEntity todo);

    @Delete
    void delete(TodoEntity todo);

    @Query("DELETE FROM TodoEntity")
    void delete();

}






