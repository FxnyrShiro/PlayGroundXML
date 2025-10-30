package com.example.topplaygroundxml.di

import com.example.topplaygroundxml.features.calculator.domain.usecase.EvaluateExpressionUseCase
import com.example.topplaygroundxml.features.weather.domain.usecase.GetWeatherUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetWeatherUseCase(get()) }
    factory { EvaluateExpressionUseCase(get()) }
}