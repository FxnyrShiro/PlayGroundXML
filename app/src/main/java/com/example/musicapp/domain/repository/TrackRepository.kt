package com.example.musicapp.domain.repository

import com.example.musicapp.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    suspend fun searchTracks(query: String): List<Track>
    suspend fun getTrack(trackId: Long): Track
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun addTrackToFavorites(track: Track)
    suspend fun removeTrackFromFavorites(trackId: Long)
    fun isFavorite(trackId: Long): Flow<Boolean>
}
