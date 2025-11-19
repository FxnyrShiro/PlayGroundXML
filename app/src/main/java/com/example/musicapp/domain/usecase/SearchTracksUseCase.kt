package com.example.musicapp.domain.usecase

import com.example.musicapp.domain.repository.TrackRepository

class SearchTracksUseCase(private val trackRepository: TrackRepository) {
    suspend operator fun invoke(query: String) = trackRepository.searchTracks(query)
}
