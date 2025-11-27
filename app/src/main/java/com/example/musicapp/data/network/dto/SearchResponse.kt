package com.example.musicapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val data: List<TrackDto>
)
