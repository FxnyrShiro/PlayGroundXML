package com.example.topplaygroundxml.data.repository

import com.example.topplaygroundxml.data.network.WeatherApi

class WeatherRepository(private val weatherApi: WeatherApi) {

    suspend fun getWeather() = weatherApi.getWeather()
}