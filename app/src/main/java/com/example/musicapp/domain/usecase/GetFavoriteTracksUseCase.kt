package com.example.musicapp.domain.usecase

import com.example.musicapp.domain.model.Track
import com.example.musicapp.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteTracksUseCase(private val trackRepository: TrackRepository) {
    operator fun invoke(): Flow<List<Track>> {
        return trackRepository.getFavoriteTracks()
    }
}
