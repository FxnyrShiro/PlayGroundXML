package com.example.musicapp.di

import androidx.room.Room
import com.example.musicapp.data.database.AppDatabase
import com.example.musicapp.data.network.ApiService
import com.example.musicapp.data.repository.TrackRepositoryImpl
import com.example.musicapp.domain.repository.TrackRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    // Network
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("https://api.deezer.com/")
            .client(get<OkHttpClient>())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    // Database
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "music_app_database"
        ).build()
    }

    single {
        get<AppDatabase>().trackDao()
    }

    // Repositories
    single<TrackRepository> { TrackRepositoryImpl(get(), get()) }
}
