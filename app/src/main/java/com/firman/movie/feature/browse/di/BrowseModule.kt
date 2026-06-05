package com.firman.movie.feature.browse.di

import com.firman.movie.feature.browse.data.remote.MovieApiService
import com.firman.movie.feature.browse.data.repository.GenreRepositoryImpl
import com.firman.movie.feature.browse.data.repository.MovieRepositoryImpl
import com.firman.movie.feature.browse.domain.repository.GenreRepository
import com.firman.movie.feature.browse.domain.repository.MovieRepository
import com.firman.movie.feature.browse.domain.usecase.GetGenresUseCase
import com.firman.movie.feature.browse.domain.usecase.GetMoviesByGenreUseCase
import com.firman.movie.feature.browse.presentation.discover.DiscoverMoviesViewModel
import com.firman.movie.feature.browse.presentation.genre.GenreListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val browseModule = module {
    // API Service
    single<MovieApiService> { get<Retrofit>().create(MovieApiService::class.java) }

    // Repositories
    single<GenreRepository> { GenreRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }

    // Use Cases
    factory { GetGenresUseCase(get()) }
    factory { GetMoviesByGenreUseCase(get()) }

    // ViewModels
    viewModel { GenreListViewModel(get()) }
    viewModel { params ->
        DiscoverMoviesViewModel(
            genreId = params.get(),
            genreName = params.get(),
            getMoviesByGenreUseCase = get(),
            getBookmarkedIdsUseCase = get(),
            toggleWatchlistUseCase = get()
        )
    }
}
