package com.example.topplaygroundxml.features.weather.domain.repository

import com.example.topplaygroundxml.features.weather.domain.model.WeatherData

interface WeatherRepository {
    suspend fun getWeather(): WeatherData
}
