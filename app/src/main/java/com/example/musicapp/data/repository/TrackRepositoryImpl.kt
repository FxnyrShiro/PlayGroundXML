package com.example.musicapp.data.repository

import com.example.musicapp.data.database.dao.TrackDao
import com.example.musicapp.data.database.entity.TrackEntity
import com.example.musicapp.data.mapper.toDomain
import com.example.musicapp.data.network.DeezerApiService
import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Track
import com.example.musicapp.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackRepositoryImpl(
    private val apiService: DeezerApiService,
    private val trackDao: TrackDao
) : TrackRepository {

    override suspend fun searchTracks(query: String): List<Track> {
        return apiService.searchTracks(query).data.map { it.toDomain() }
    }

    override suspend fun getTrack(trackId: Long): Track {
        return apiService.getTrack(trackId).toDomain()
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getAllTracks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addTrackToFavorites(track: Track) {
        trackDao.insertTrack(track.toEntity())
    }

    override suspend fun removeTrackFromFavorites(trackId: Long) {
        trackDao.deleteTrack(trackId)
    }

    override fun isFavorite(trackId: Long): Flow<Boolean> {
        return trackDao.isFavorite(trackId)
    }

    private fun Track.toEntity(): TrackEntity {
        return TrackEntity(
            id = this.id,
            title = this.title,
            artistName = this.artist?.name ?: "Unknown Artist",
            albumCover = this.album?.coverMedium ?: "",
            albumTitle = this.album?.title ?: "",
            previewUrl = this.preview ?: ""
        )
    }

    private fun TrackEntity.toDomain(): Track {
        return Track(
            id = this.id,
            title = this.title,
            preview = this.previewUrl,
            artist = Artist(
                id = 0,
                name = this.artistName,
                picture = null,
                pictureMedium = null
            ),
            album = Album(
                id = 0,
                title = this.albumTitle,
                cover = this.albumCover,
                coverMedium = this.albumCover
            ),
            releaseDate = null,
            duration = null,
            explicitLyrics = null
        )
    }
}
