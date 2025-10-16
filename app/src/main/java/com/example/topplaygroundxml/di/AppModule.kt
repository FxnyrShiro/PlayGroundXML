package com.example.topplaygroundxml.di

import com.example.topplaygroundxml.data.Navigator
import com.example.topplaygroundxml.ui.main.MainViewModel
import com.example.topplaygroundxml.ui.second.SecondViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Navigator() }

    viewModel { MainViewModel(navigator = get()) }
    viewModel { SecondViewModel() }
}