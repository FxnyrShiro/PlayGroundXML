package com.example.musicapp.domain.usecase

import com.example.musicapp.domain.repository.TrackRepository

class GetTrackDetailUseCase(private val repository: TrackRepository) {
    suspend operator fun invoke(trackId: Long) = repository.getTrack(trackId)
}
