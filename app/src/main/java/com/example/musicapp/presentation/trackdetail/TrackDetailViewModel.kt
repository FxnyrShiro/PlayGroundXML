package com.example.musicapp.presentation.trackdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.network.dto.TrackDto
import com.example.musicapp.domain.usecase.GetTrackDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface TrackDetailUiState {
    object Loading : TrackDetailUiState
    data class Success(val track: TrackDto) : TrackDetailUiState
    data class Error(val message: String) : TrackDetailUiState
}

class TrackDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTrackDetailUseCase: GetTrackDetailUseCase
) : ViewModel() {

    private val trackId: String = savedStateHandle.get<String>("trackId")!!

    private val _uiState = MutableStateFlow<TrackDetailUiState>(TrackDetailUiState.Loading)
    val uiState: StateFlow<TrackDetailUiState> = _uiState.asStateFlow()

    init {
        loadTrackDetails()
    }

    private fun loadTrackDetails() {
        viewModelScope.launch {
            _uiState.value = TrackDetailUiState.Loading
            try {
                val track = getTrackDetailUseCase(trackId.toLong())
                _uiState.value = TrackDetailUiState.Success(track)
            } catch (e: Exception) {
                _uiState.value = TrackDetailUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}