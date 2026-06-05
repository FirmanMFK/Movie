package com.firman.movie.feature.watchlist.domain.usecase

import com.firman.movie.feature.watchlist.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow

class GetBookmarkedIdsUseCase(private val repository: WatchlistRepository) {
    operator fun invoke(): Flow<Set<Int>> = repository.getBookmarkedIds()
}
