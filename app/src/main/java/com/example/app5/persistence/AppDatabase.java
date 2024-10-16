package com.example.app5.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PlayerEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDAO playerDAO();
}

