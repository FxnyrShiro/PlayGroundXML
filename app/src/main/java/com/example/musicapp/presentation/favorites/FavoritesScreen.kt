package com.example.musicapp.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.domain.model.Track
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.favorites_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
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
        Text(
            text = stringResource(id = R.string.favorites_empty_state),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun FavoritesList(
    tracks: List<Track>,
    onRemoveClick: (Long) -> Unit,
    onTrackClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(tracks, key = { it.id }) { track ->
            TrackListItem(
                track = track,
                isFavorite = true, // All tracks here are favorites
                onFavoriteClick = { onRemoveClick(track.id) },
                onItemClick = { onTrackClick(track.id) }
            )
        }
    }
}
