package com.firman.movie.feature.watchlist.di

import com.firman.movie.feature.watchlist.data.repository.WatchlistRepositoryImpl
import com.firman.movie.feature.watchlist.domain.repository.WatchlistRepository
import com.firman.movie.feature.watchlist.domain.usecase.GetBookmarkedIdsUseCase
import com.firman.movie.feature.watchlist.domain.usecase.GetWatchlistUseCase
import com.firman.movie.feature.watchlist.domain.usecase.ToggleWatchlistUseCase
import com.firman.movie.feature.watchlist.presentation.WatchlistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val watchlistModule = module {
    single<WatchlistRepository> { WatchlistRepositoryImpl(androidContext()) }

    factory { GetWatchlistUseCase(get()) }
    factory { GetBookmarkedIdsUseCase(get()) }
    factory { ToggleWatchlistUseCase(get()) }

    viewModel { WatchlistViewModel(get(), get(), get()) }
}
