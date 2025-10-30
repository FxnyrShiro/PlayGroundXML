package com.example.topplaygroundxml.features.auth.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.topplaygroundxml.features.auth.data.local.dao.UserDao
import com.example.topplaygroundxml.features.auth.data.local.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
