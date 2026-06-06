package com.firman.movie.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object GenreList : Screen

    @Serializable
    data class DiscoverMovies(
        val genreId: Int,
        val genreName: String
    ) : Screen

    @Serializable
    data object Watchlist : Screen

    @Serializable
    data class MovieDetail(val movieId: Int) : Screen

    @Serializable
    data class Trailer(val movieId: Int) : Screen
    
    @Serializable
    data object Search : Screen
}
