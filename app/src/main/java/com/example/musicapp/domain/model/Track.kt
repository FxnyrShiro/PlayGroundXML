package com.example.musicapp.domain.model

data class Track(
    val id: Long,
    val title: String,
    val preview: String?,
    val artist: Artist?,
    val album: Album?,
    val releaseDate: String?,
    val duration: Int?,
    val explicitLyrics: Boolean?
)
