package com.example.musicapp.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicapp.R
import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Track
import com.example.musicapp.ui.theme.MusicAppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onTrackClick: (Long) -> Unit
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text(stringResource(id = R.string.search_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val state = uiState) {
                is SearchUiState.Idle -> IdleState()
                is SearchUiState.Loading -> LoadingState()
                is SearchUiState.Success -> {
                    val favoriteTrackIds by viewModel.favoriteTrackIds.collectAsState()
                    TrackList(
                        tracks = state.tracks,
                        favoriteTrackIds = favoriteTrackIds,
                        onFavoriteClick = viewModel::toggleFavorite,
                        onTrackClick = onTrackClick
                    )
                }
                is SearchUiState.Error -> ErrorState(message = state.message)
            }
        }
    }
}

@Composable
private fun IdleState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.search_idle_text),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
private fun TrackList(
    tracks: List<Track>,
    favoriteTrackIds: Set<Long>,
    onFavoriteClick: (Track) -> Unit,
    onTrackClick: (Long) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(tracks) { track ->
            TrackListItem(
                track = track,
                isFavorite = favoriteTrackIds.contains(track.id),
                onFavoriteClick = { onFavoriteClick(track) },
                onItemClick = { onTrackClick(track.id) }
            )
        }
    }
}

@Composable
fun TrackListItem(
    track: Track,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = track.album?.coverMedium,
            contentDescription = stringResource(id = R.string.album_cover_desc),
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = track.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = track.artist?.name ?: stringResource(id = R.string.unknown_artist),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = stringResource(id = R.string.toggle_favorite_desc),
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// --- Previews ---

private val previewTrack = Track(
    id = 1,
    title = "Stairway to Heaven",
    preview = "",
    artist = Artist(0, "Led Zeppelin", null, null),
    album = Album(
        id = 0, 
        title = "Led Zeppelin IV", 
        cover = "", 
        coverMedium = "https://e-cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/250x250-000000-80-0-0.jpg"
    ),
    releaseDate = "1971-11-08",
    duration = 482,
    explicitLyrics = false
)

@Preview(name = "Track Item", showBackground = true)
@Composable
private fun TrackListItemPreview() {
    MusicAppTheme {
        TrackListItem(
            track = previewTrack,
            isFavorite = false,
            onFavoriteClick = {},
            onItemClick = {}
        )
    }
}

@Preview(name = "Track Item (Favorite)", showBackground = true)
@Composable
private fun TrackListItemFavoritePreview() {
    MusicAppTheme {
        TrackListItem(
            track = previewTrack,
            isFavorite = true,
            onFavoriteClick = {},
            onItemClick = {}
        )
    }
}

@Preview(name = "Idle State", showBackground = true)
@Composable
private fun IdleStatePreview() {
    MusicAppTheme {
        IdleState()
    }
}

@Preview(name = "Loading State", showBackground = true)
@Composable
private fun LoadingStatePreview() {
    MusicAppTheme {
        LoadingState()
    }
}

@Preview(name = "Error State", showBackground = true)
@Composable
private fun ErrorStatePreview() {
    MusicAppTheme {
        ErrorState(message = "Failed to load tracks. Please try again.")
    }
}
