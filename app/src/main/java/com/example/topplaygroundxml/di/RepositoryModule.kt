package com.example.topplaygroundxml.di

import com.example.topplaygroundxml.data.repository.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherRepository(get()) }
}