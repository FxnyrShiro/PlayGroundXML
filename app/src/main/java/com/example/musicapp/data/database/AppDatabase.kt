package com.example.musicapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.data.database.dao.TrackDao
import com.example.musicapp.data.database.entity.TrackEntity

@Database(entities = [TrackEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}
