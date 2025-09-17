package com.sahak7an.t3.feature.favorites.di

import com.sahak7an.t3.feature.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoritesViewModel(get()) }
}


