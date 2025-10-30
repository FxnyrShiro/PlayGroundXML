package com.example.topplaygroundxml.features.weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topplaygroundxml.features.weather.domain.model.WeatherData
import com.example.topplaygroundxml.features.weather.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherState {
    object Loading : WeatherState()
    data class Success(val weatherData: WeatherData) : WeatherState()
    data class Error(val message: String) : WeatherState()
}

class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState = _weatherState.asStateFlow()

    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = WeatherState.Loading
            try {
                val weather = getWeatherUseCase()
                _weatherState.value = WeatherState.Success(weather)
            } catch (e: Exception) {
                _weatherState.value = WeatherState.Error(e.message ?: "Unknown error")
            }
        }
    }
}