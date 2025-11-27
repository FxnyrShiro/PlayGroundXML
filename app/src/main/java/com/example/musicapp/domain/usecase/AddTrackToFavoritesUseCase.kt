package com.example.musicapp.domain.usecase

import com.example.musicapp.data.database.entity.TrackEntity
import com.example.musicapp.data.network.dto.TrackDto
import com.example.musicapp.domain.repository.TrackRepository

class AddTrackToFavoritesUseCase(private val trackRepository: TrackRepository) {
    suspend operator fun invoke(track: TrackDto) {
        val trackEntity = TrackEntity(
            id = track.id,
            title = track.title,
            artistName = track.artist?.name ?: "Unknown Artist",
            albumCover = track.album?.coverMedium ?: "",
            albumTitle = track.album?.title ?: "",
            previewUrl = track.preview ?: ""
        )
        trackRepository.addTrackToFavorites(trackEntity)
    }
}
