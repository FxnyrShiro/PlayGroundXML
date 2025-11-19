package com.example.musicapp.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicapp.data.network.dto.TrackDto
import com.example.musicapp.data.database.entity.TrackEntity
import com.example.musicapp.presentation.search.TrackListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel = koinViewModel()) {
    val favoriteTracks by viewModel.favoriteTracks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorite Tracks") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (favoriteTracks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "You haven\'t added any tracks to favorites yet.")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(favoriteTracks) { trackEntity ->
                        val trackDto = trackEntity.toDto()
                        TrackListItem(
                            track = trackDto,
                            isFavorite = true, // All tracks here are favorites
                            onFavoriteClick = { viewModel.removeFromFavorites(trackEntity.id) }
                        )
                    }
                }
            }
        }
    }
}

// Simple extension function to convert TrackEntity to TrackDto
private fun TrackEntity.toDto(): TrackDto {
    return TrackDto(
        id = this.id,
        title = this.title,
        artist = com.example.musicapp.data.network.dto.ArtistDto(id = 0, name = this.artistName),
        album = com.example.musicapp.data.network.dto.AlbumDto(id = 0, coverMedium = this.albumCover),
        preview = this.previewUrl
    )
}
