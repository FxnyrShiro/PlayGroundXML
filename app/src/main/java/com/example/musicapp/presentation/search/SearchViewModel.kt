package com.example.musicapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.network.dto.TrackDto
import com.example.musicapp.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface SearchUiState {
    object Idle : SearchUiState
    object Loading : SearchUiState
    data class Success(val tracks: List<TrackDto>) : SearchUiState
    data class Error(val message: String) : SearchUiState
}

class SearchViewModel(
    private val searchTracksUseCase: SearchTracksUseCase,
    private val addTrackToFavoritesUseCase: AddTrackToFavoritesUseCase,
    private val removeTrackFromFavoritesUseCase: RemoveTrackFromFavoritesUseCase,
    getFavoriteTracksUseCase: GetFavoriteTracksUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    val favoriteTrackIds: StateFlow<Set<Long>> = getFavoriteTracksUseCase()
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        observeSearchQuery()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(500) // Wait for 500ms of silence
                .distinctUntilChanged() // Only search if the query is new
                .collect { query ->
                    if (query.isBlank()) {
                        _uiState.value = SearchUiState.Idle
                    } else {
                        searchTracks(query)
                    }
                }
        }
    }

    private fun searchTracks(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            try {
                val tracks = searchTracksUseCase(query)
                _uiState.value = SearchUiState.Success(tracks)
            } catch (e: Exception) {
                _uiState.value = SearchUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun toggleFavorite(track: TrackDto) {
        viewModelScope.launch {
            if (favoriteTrackIds.value.contains(track.id)) {
                removeTrackFromFavoritesUseCase(track.id)
            } else {
                addTrackToFavoritesUseCase(track)
            }
        }
    }
}
