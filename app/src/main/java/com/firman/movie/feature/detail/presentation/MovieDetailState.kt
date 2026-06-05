package com.firman.movie.feature.detail.presentation

import com.firman.movie.feature.detail.domain.model.MovieDetail

sealed interface MovieDetailState {
    data object Loading : MovieDetailState
    data class Success(
        val movie: MovieDetail,
        val isWatchlisted: Boolean = false
    ) : MovieDetailState
    data class Error(val message: String) : MovieDetailState
}
