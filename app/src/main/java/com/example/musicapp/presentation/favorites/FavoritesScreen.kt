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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Track
import com.example.musicapp.presentation.search.TrackListItem
import com.example.musicapp.ui.theme.MusicAppTheme
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
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()
        ) {
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.favorites_empty_state),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
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

// --- Previews ---

@Preview(name = "Favorites List", showBackground = true)
@Composable
private fun FavoritesListPreview() {
    MusicAppTheme {
        FavoritesList(
            tracks = listOf(
                Track(1, "Waka Waka (This Time for Africa)", "", Artist(0, "Shakira", null, null), Album(0, "Waka Waka", "", ""), "2010-06-07", 210, false),
                Track(2, "Smells Like Teen Spirit", "", Artist(0, "Nirvana", null, null), Album(0, "Nevermind", "", ""), "1991-09-10", 301, false),
                Track(3, "Billie Jean", "", Artist(0, "Michael Jackson", null, null), Album(0, "Thriller", "", ""), "1983-01-02", 294, false)
            ),
            onRemoveClick = {},
            onTrackClick = {}
        )
    }
}

@Preview(name = "Empty State", showBackground = true)
@Composable
private fun EmptyStatePreview() {
    MusicAppTheme {
        EmptyState()
    }
}
