package com.example.musicapp.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicapp.data.database.entity.TrackEntity
import com.example.musicapp.data.network.dto.AlbumDto
import com.example.musicapp.data.network.dto.ArtistDto
import com.example.musicapp.data.network.dto.TrackDto
import com.example.musicapp.presentation.search.TrackListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    onTrackClick: (Long) -> Unit
) {
    val favoriteTracks by viewModel.favoriteTracks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Tracks") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (favoriteTracks.isEmpty()) {
                EmptyState()
            } else {
                FavoritesList(
                    tracks = favoriteTracks,
                    onRemoveClick = { trackId -> viewModel.removeFromFavorites(trackId) },
                    onTrackClick = onTrackClick
                )
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "You haven\'t added any tracks to favorites yet.")
    }
}

@Composable
private fun FavoritesList(
    tracks: List<TrackEntity>,
    onRemoveClick: (Long) -> Unit,
    onTrackClick: (Long) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tracks) { trackEntity ->
            val trackDto = trackEntity.toDto()
            TrackListItem(
                track = trackDto,
                isFavorite = true, // All tracks here are favorites
                onFavoriteClick = { onRemoveClick(trackEntity.id) },
                onItemClick = { onTrackClick(trackEntity.id) }
            )
        }
    }
}

private fun TrackEntity.toDto(): TrackDto {
    return TrackDto(
        id = this.id,
        title = this.title,
        artist = ArtistDto(id = 0, name = this.artistName),
        album = AlbumDto(id = 0, coverMedium = this.albumCover),
        preview = this.previewUrl
    )
}
