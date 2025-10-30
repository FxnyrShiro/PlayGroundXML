package com.example.topplaygroundxml.di

import com.example.topplaygroundxml.features.calculator.domain.repository.CalculationRepository
import com.example.topplaygroundxml.features.weather.data.remote.WeatherApi
import com.example.topplaygroundxml.features.weather.domain.repository.WeatherRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.objecthunter.exp4j.ExpressionBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    // Network
    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(get<OkHttpClient>())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    single<WeatherApi> {
        get<Retrofit>().create(WeatherApi::class.java)
    }

    // Repositories
    single<WeatherRepository> {
        object : WeatherRepository {
            override suspend fun getWeather() = get<WeatherApi>().getWeather()
        }
    }

    single<CalculationRepository> {
        object : CalculationRepository {
            override fun evaluateExpression(expression: String): Double {
                return ExpressionBuilder(expression).build().evaluate()
            }
        }
    }
}