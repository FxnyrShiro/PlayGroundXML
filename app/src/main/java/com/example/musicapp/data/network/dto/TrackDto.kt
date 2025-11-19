package com.example.musicapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("preview") val preview: String? = null,
    @SerialName("artist") val artist: ArtistDto? = null,
    @SerialName("album") val album: AlbumDto? = null
)
