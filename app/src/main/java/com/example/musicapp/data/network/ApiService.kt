package com.example.musicapp.data.network

import com.example.musicapp.data.network.dto.SearchResponseDto
import com.example.musicapp.data.network.dto.TrackDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun searchTracks(@Query("q") query: String): SearchResponseDto

    @GET("track/{id}")
    suspend fun getTrack(@Path("id") trackId: Long): TrackDto
}
