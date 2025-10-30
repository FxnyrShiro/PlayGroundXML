package com.example.topplaygroundxml.di

import androidx.room.Room
import com.example.topplaygroundxml.features.auth.data.local.database.AppDatabase
import com.example.topplaygroundxml.features.auth.data.repository.AuthRepositoryImpl
import com.example.topplaygroundxml.features.auth.domain.repository.AuthRepository
import com.example.topplaygroundxml.features.calculator.domain.repository.CalculationRepository
import com.example.topplaygroundxml.features.weather.data.remote.WeatherApi
import com.example.topplaygroundxml.features.weather.domain.repository.WeatherRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.objecthunter.exp4j.ExpressionBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    // Database (Room)
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single {
        get<AppDatabase>().userDao()
    }

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
    
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}