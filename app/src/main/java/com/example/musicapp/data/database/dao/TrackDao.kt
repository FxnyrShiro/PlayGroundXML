package com.example.musicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("DELETE FROM tracks WHERE id = :trackId")
    suspend fun deleteTrack(trackId: Long)

    @Query("SELECT * FROM tracks")
    fun getAllTracks(): Flow<List<TrackEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM tracks WHERE id = :trackId LIMIT 1)")
    fun isFavorite(trackId: Long): Flow<Boolean>
}
