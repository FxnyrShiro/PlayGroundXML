package com.example.topplaygroundxml.di

import com.example.topplaygroundxml.data.Navigator
import com.example.topplaygroundxml.features.calculator.domain.usecase.EvaluateExpressionUseCase
import com.example.topplaygroundxml.features.calculator.presentation.viewmodel.CalculatorViewModel
import com.example.topplaygroundxml.features.main.presentation.viewmodel.MainViewModel
import com.example.topplaygroundxml.features.second.presentation.viewmodel.SecondViewModel
import com.example.topplaygroundxml.features.weather.domain.usecase.GetWeatherUseCase
import com.example.topplaygroundxml.features.weather.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    single { Navigator() }

    viewModel { MainViewModel(get<Navigator>()) }
    viewModel { SecondViewModel() }
    viewModel { WeatherViewModel(get<GetWeatherUseCase>()) }
    viewModel { CalculatorViewModel(get<EvaluateExpressionUseCase>()) }
}