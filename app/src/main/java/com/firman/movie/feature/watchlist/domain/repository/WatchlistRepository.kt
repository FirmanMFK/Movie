package com.firman.movie.feature.watchlist.domain.repository

import com.firman.movie.feature.browse.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    fun getWatchlist(): Flow<List<Movie>>
    fun getBookmarkedIds(): Flow<Set<Int>>
    suspend fun toggleWatchlist(movie: Movie)
}
