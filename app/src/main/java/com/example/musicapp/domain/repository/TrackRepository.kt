package com.example.musicapp.domain.repository

import com.example.musicapp.data.database.entity.TrackEntity
import com.example.musicapp.data.network.dto.TrackDto
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    suspend fun searchTracks(query: String): List<TrackDto>
    suspend fun getTrack(trackId: Long): TrackDto
    fun getFavoriteTracks(): Flow<List<TrackEntity>>
    suspend fun addTrackToFavorites(track: TrackEntity)
    suspend fun removeTrackFromFavorites(trackId: Long)
    fun isFavorite(trackId: Long): Flow<Boolean>
}
