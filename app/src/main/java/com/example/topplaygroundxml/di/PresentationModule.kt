package com.example.topplaygroundxml.di

import com.example.topplaygroundxml.features.auth.domain.usecase.LoginUserUseCase
import com.example.topplaygroundxml.features.auth.domain.usecase.RegisterUserUseCase
import com.example.topplaygroundxml.features.auth.presentation.viewmodel.AuthViewModel
import com.example.topplaygroundxml.features.calculator.domain.usecase.EvaluateExpressionUseCase
import com.example.topplaygroundxml.features.calculator.presentation.viewmodel.CalculatorViewModel
import com.example.topplaygroundxml.features.list.presentation.viewmodel.ListViewModel
import com.example.topplaygroundxml.features.weather.domain.usecase.GetWeatherUseCase
import com.example.topplaygroundxml.features.weather.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { WeatherViewModel(get<GetWeatherUseCase>()) }
    viewModel { CalculatorViewModel(get<EvaluateExpressionUseCase>()) }
    viewModel { AuthViewModel(get<LoginUserUseCase>(), get<RegisterUserUseCase>()) }
    viewModel { ListViewModel() }
}