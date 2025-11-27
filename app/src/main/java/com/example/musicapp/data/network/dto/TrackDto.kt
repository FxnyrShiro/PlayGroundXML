package com.example.musicapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    val id: Long,
    val title: String,
    @SerialName("title_short") val titleShort: String? = null,
    @SerialName("title_version") val titleVersion: String? = null,
    val link: String? = null,
    val duration: Int? = null,
    val rank: Int? = null,
    @SerialName("explicit_lyrics") val explicitLyrics: Boolean? = null,
    val preview: String? = null,
    val artist: ArtistDto? = null,
    val album: AlbumDto? = null,
    val releaseDate: String? = null // This field is not in the JSON, but might be useful later
)
