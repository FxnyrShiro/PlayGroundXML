package com.example.musicapp.data.mapper

import com.example.musicapp.data.network.dto.AlbumDto
import com.example.musicapp.data.network.dto.ArtistDto
import com.example.musicapp.data.network.dto.TrackDto
import com.example.musicapp.domain.model.Album
import com.example.musicapp.domain.model.Artist
import com.example.musicapp.domain.model.Track

fun TrackDto.toDomain(): Track {
    return Track(
        id = this.id,
        title = this.title,
        preview = this.preview,
        artist = this.artist?.toDomain(),
        album = this.album?.toDomain(),
        releaseDate = this.releaseDate,
        duration = this.duration,
        explicitLyrics = this.explicitLyrics
    )
}

fun ArtistDto.toDomain(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        picture = this.picture,
        pictureMedium = this.pictureMedium
    )
}

fun AlbumDto.toDomain(): Album {
    return Album(
        id = this.id,
        title = this.title,
        cover = this.cover,
        coverMedium = this.coverMedium
    )
}
