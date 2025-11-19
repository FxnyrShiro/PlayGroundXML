package com.example.musicapp.domain.usecase

import com.example.musicapp.domain.repository.TrackRepository

class RemoveTrackFromFavoritesUseCase(private val trackRepository: TrackRepository) {
    suspend operator fun invoke(trackId: Long) = trackRepository.removeTrackFromFavorites(trackId)
}
