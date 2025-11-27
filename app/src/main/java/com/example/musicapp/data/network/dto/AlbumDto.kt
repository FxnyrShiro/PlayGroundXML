package com.example.musicapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    val id: Long,
    val title: String,
    val cover: String? = null,
    @SerialName("cover_small") val coverSmall: String? = null,
    @SerialName("cover_medium") val coverMedium: String? = null,
    @SerialName("cover_big") val coverBig: String? = null,
    @SerialName("cover_xl") val coverXl: String? = null,
    val tracklist: String? = null
)
