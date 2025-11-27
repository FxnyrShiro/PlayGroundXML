package com.example.musicapp.data.network

import com.example.musicapp.data.network.dto.SearchResponse
import com.example.musicapp.data.network.dto.TrackDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("search/track")
    suspend fun searchTracks(@Query("q") query: String): SearchResponse

    @GET("track/{id}")
    suspend fun getTrack(@Path("id") trackId: Long): TrackDto
}
