package com.firman.movie.feature.browse.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.browse.domain.usecase.GetMoviesByGenreUseCase
import com.firman.movie.feature.watchlist.domain.usecase.GetBookmarkedIdsUseCase
import com.firman.movie.feature.watchlist.domain.usecase.ToggleWatchlistUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DiscoverMoviesViewModel(
    private val genreId: Int,
    val genreName: String,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    getBookmarkedIdsUseCase: GetBookmarkedIdsUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase
) : ViewModel() {

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

    private val _state = MutableStateFlow(DiscoverMoviesState())
    val state: StateFlow<DiscoverMoviesState> = _state.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: Flow<PagingData<Movie>> = _state
        .flatMapLatest { currentState ->
            getMoviesByGenreUseCase(genreId, currentState.selectedSort.apiValue)
        }
        .cachedIn(viewModelScope)

    fun onSortChanged(sort: SortFilter) {
        _state.update { it.copy(selectedSort = sort) }
    }
}
