package com.example.musicapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null
)
