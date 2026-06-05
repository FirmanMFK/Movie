package com.firman.movie.feature.watchlist.domain.usecase

import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.watchlist.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow

class GetWatchlistUseCase(private val repository: WatchlistRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.getWatchlist()
}
