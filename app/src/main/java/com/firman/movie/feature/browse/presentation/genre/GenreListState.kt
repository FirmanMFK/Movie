package com.firman.movie.feature.browse.presentation.genre

import com.firman.movie.feature.browse.domain.model.Genre

sealed interface GenreListState {
    data object Loading : GenreListState
    data class Success(val genres: List<Genre>) : GenreListState
    data class Error(val message: String) : GenreListState
}
