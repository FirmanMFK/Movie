package com.firman.movie.feature.watchlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.watchlist.domain.usecase.GetBookmarkedIdsUseCase
import com.firman.movie.feature.watchlist.domain.usecase.GetWatchlistUseCase
import com.firman.movie.feature.watchlist.domain.usecase.ToggleWatchlistUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WatchlistViewModel(
    getWatchlistUseCase: GetWatchlistUseCase,
    getBookmarkedIdsUseCase: GetBookmarkedIdsUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase
) : ViewModel() {

    val watchlist: StateFlow<List<Movie>> = getWatchlistUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val bookmarkedIds: StateFlow<Set<Int>> = getBookmarkedIdsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    fun onToggleBookmark(movie: Movie) {
        viewModelScope.launch {
            toggleWatchlistUseCase(movie)
        }
    }
}
