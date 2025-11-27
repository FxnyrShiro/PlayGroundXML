package com.example.musicapp.domain.usecase

import com.example.musicapp.domain.model.Track
import com.example.musicapp.domain.repository.TrackRepository

class AddTrackToFavoritesUseCase(private val trackRepository: TrackRepository) {
    suspend operator fun invoke(track: Track) {
        trackRepository.addTrackToFavorites(track)
    }
}