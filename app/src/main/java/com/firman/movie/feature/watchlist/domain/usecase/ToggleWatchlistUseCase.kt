package com.firman.movie.feature.watchlist.domain.usecase

import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.watchlist.domain.repository.WatchlistRepository

class ToggleWatchlistUseCase(private val repository: WatchlistRepository) {
    suspend operator fun invoke(movie: Movie) = repository.toggleWatchlist(movie)
}
