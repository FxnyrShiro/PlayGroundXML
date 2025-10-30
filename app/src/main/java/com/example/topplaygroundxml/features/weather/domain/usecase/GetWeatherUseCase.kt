package com.example.topplaygroundxml.features.weather.domain.usecase

import com.example.topplaygroundxml.features.weather.domain.repository.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke() = weatherRepository.getWeather()
}