package com.example.musicapp.di

import androidx.room.Room
import com.example.musicapp.data.database.AppDatabase
import com.example.musicapp.data.network.DeezerApiService
import com.example.musicapp.data.repository.TrackRepositoryImpl
import com.example.musicapp.domain.repository.TrackRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "music_app_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    single { get<AppDatabase>().trackDao() }

    // Network
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
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

    single<DeezerApiService> {
        get<Retrofit>().create(DeezerApiService::class.java)
    }

    // Repository
    single<TrackRepository> { TrackRepositoryImpl(get(), get()) }

}
