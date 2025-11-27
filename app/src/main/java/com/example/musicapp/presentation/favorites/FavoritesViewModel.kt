package com.example.musicapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.domain.model.Track
import com.example.musicapp.domain.usecase.GetFavoriteTracksUseCase
import com.example.musicapp.domain.usecase.RemoveTrackFromFavoritesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    getFavoriteTracksUseCase: GetFavoriteTracksUseCase,
    private val removeTrackFromFavoritesUseCase: RemoveTrackFromFavoritesUseCase
) : ViewModel() {

    val favoriteTracks: StateFlow<List<Track>> = getFavoriteTracksUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFromFavorites(trackId: Long) {
        viewModelScope.launch {
            removeTrackFromFavoritesUseCase(trackId)
        }
    }
}
