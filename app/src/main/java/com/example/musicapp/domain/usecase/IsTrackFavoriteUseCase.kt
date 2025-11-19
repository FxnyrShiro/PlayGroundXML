package com.example.musicapp.domain.usecase

import com.example.musicapp.domain.repository.TrackRepository

class IsTrackFavoriteUseCase(private val trackRepository: TrackRepository) {
    operator fun invoke(trackId: Long) = trackRepository.isFavorite(trackId)
}
