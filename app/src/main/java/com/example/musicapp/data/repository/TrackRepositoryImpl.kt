package com.example.musicapp.data.repository

import com.example.musicapp.data.database.dao.TrackDao
import com.example.musicapp.data.database.entity.TrackEntity
import com.example.musicapp.data.network.DeezerApiService
import com.example.musicapp.data.network.dto.TrackDto
import com.example.musicapp.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow

class TrackRepositoryImpl(
    private val apiService: DeezerApiService,
    private val trackDao: TrackDao
) : TrackRepository {

    override suspend fun searchTracks(query: String): List<TrackDto> {
        return apiService.searchTracks(query).data
    }

    override suspend fun getTrack(trackId: Long): TrackDto {
        // This should ideally return a domain model, not a DTO
        return apiService.getTrack(trackId)
    }

    override fun getFavoriteTracks(): Flow<List<TrackEntity>> {
        return trackDao.getAllTracks()
    }

    override suspend fun addTrackToFavorites(track: TrackEntity) {
        trackDao.insertTrack(track)
    }

    override suspend fun removeTrackFromFavorites(trackId: Long) {
        trackDao.deleteTrack(trackId)
    }

    override fun isFavorite(trackId: Long): Flow<Boolean> {
        return trackDao.isFavorite(trackId)
    }
}
