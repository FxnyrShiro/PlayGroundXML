package com.example.musicapp.di

import com.example.musicapp.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory { SearchTracksUseCase(get()) }
    factory { GetFavoriteTracksUseCase(get()) }
    factory { AddTrackToFavoritesUseCase(get()) }
    factory { RemoveTrackFromFavoritesUseCase(get()) }
    factory { IsTrackFavoriteUseCase(get()) }
    factory { GetTrackDetailUseCase(get()) }
}
