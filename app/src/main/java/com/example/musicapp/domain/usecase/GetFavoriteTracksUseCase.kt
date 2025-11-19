package com.example.musicapp.domain.usecase

import com.example.musicapp.domain.repository.TrackRepository

class GetFavoriteTracksUseCase(private val trackRepository: TrackRepository) {
    operator fun invoke() = trackRepository.getFavoriteTracks()
}
