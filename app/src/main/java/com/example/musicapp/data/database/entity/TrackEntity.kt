package com.example.musicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artistName: String,
    val albumCover: String,
    val previewUrl: String
)
