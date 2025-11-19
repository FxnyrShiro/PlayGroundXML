package com.example.musicapp.di

import com.example.musicapp.presentation.favorites.FavoritesViewModel
import com.example.musicapp.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SearchViewModel(get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
}
