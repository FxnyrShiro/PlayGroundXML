package com.example.musicapp.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("data") val data: List<TrackDto>
)
