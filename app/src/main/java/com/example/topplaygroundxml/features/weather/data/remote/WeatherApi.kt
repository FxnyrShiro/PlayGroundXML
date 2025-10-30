package com.example.topplaygroundxml.features.weather.data.remote

import com.example.topplaygroundxml.features.weather.domain.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double = 55.75,
        @Query("longitude") longitude: Double = 37.62,
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum",
        @Query("timezone") timezone: String = "auto"
    ): WeatherData
}