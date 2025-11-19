package com.example.musicapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String? = null,
    @SerialName("cover_medium") val coverMedium: String? = null
)
