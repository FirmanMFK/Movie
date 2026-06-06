package com.firman.movie.feature.detail.di

import com.firman.movie.feature.detail.data.repository.MovieDetailRepositoryImpl
import com.firman.movie.feature.detail.domain.repository.MovieDetailRepository
import com.firman.movie.feature.detail.presentation.MovieDetailViewModel
import com.firman.movie.feature.detail.presentation.trailer.TrailerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    single<MovieDetailRepository> { MovieDetailRepositoryImpl(get()) }

    viewModel { params ->
        MovieDetailViewModel(
            movieId = params.get(),
            repository = get(),
            watchlistRepository = get()
        )
    }

    viewModel { params ->
        TrailerViewModel(
            movieId = params.get(),
            movieDetailRepository = get()
        )
    }
}
