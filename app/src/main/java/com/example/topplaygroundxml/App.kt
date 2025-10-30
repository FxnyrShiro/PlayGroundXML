package com.example.topplaygroundxml

import android.app.Application
import com.example.topplaygroundxml.di.appModule
import com.example.topplaygroundxml.di.networkModule
import com.example.topplaygroundxml.di.repositoryModule
import com.example.topplaygroundxml.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, networkModule, repositoryModule, viewModelModule)
        }
    }
}