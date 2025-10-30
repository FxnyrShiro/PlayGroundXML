package com.example.topplaygroundxml.features.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val daily: DailyData
)

@Serializable
data class DailyData(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val precipitation_sum: List<Double>,
    val weather_code: List<Int>
)
