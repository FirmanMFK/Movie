package com.firman.movie.feature.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.detail.domain.model.Review
import com.firman.movie.feature.detail.domain.repository.MovieDetailRepository
import com.firman.movie.feature.watchlist.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val repository: MovieDetailRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    val reviews: Flow<PagingData<Review>> = repository.getMovieReviews(movieId)
        .cachedIn(viewModelScope)

    init {
        loadMovieDetail()
        observeWatchlist()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            _state.value = MovieDetailState.Loading
            val watchlistedIds = watchlistRepository.getBookmarkedIds().first()
            val isWatchlisted = watchlistedIds.contains(movieId)

            repository.getMovieDetail(movieId).fold(
                onSuccess = { detail ->
                    _state.value = MovieDetailState.Success(detail, isWatchlisted)
                },
                onFailure = { exception ->
                    _state.value = MovieDetailState.Error(
                        exception.message ?: "Failed to load movie details"
                    )
                }
            )
        }
    }

    private fun observeWatchlist() {
        viewModelScope.launch {
            watchlistRepository.getBookmarkedIds().collect { ids ->
                val currentState = _state.value
                if (currentState is MovieDetailState.Success) {
                    _state.value = currentState.copy(isWatchlisted = ids.contains(movieId))
                }
            }
        }
    }

    fun toggleWatchlist() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is MovieDetailState.Success) {
                val detail = currentState.movie
                val movie = Movie(
                    id = detail.id,
                    title = detail.title,
                    overview = detail.overview,
                    posterPath = detail.posterPath,
                    backdropPath = detail.backdropPath,
                    voteAverage = detail.voteAverage,
                    releaseDate = detail.releaseDate
                )
                watchlistRepository.toggleWatchlist(movie)
            }
        }
    }
}
