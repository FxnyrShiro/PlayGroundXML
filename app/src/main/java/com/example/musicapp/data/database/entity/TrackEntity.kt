package com.example.musicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tracks")
data class TrackEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artistName: String,
    val albumCover: String,
    val albumTitle: String, // Added this field
    val previewUrl: String
)
