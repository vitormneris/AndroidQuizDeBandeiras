package com.example.app5.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerDAO {
    @Query("SELECT * FROM playerentity")
    LiveData<List<PlayerEntity>> findAll();
    @Insert
    void save(PlayerEntity player);
}
