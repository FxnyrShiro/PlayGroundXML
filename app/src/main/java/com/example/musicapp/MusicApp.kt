package com.example.musicapp

import android.app.Application
import com.example.musicapp.di.appModule
import com.example.musicapp.di.dataModule
import com.example.musicapp.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MusicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MusicApp)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}