package com.firman.movie.feature.detail.presentation.trailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.movie.feature.detail.domain.model.MovieDetail
import com.firman.movie.feature.detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TrailerState {
    object Loading : TrailerState()
    data class Success(
        val movieDetail: MovieDetail
    ) : TrailerState()
    data class Error(val message: String) : TrailerState()
}

class TrailerViewModel(
    private val movieId: Int,
    private val movieDetailRepository: MovieDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<TrailerState>(TrailerState.Loading)
    val state: StateFlow<TrailerState> = _state.asStateFlow()

    init {
        loadTrailerData()
    }

    fun loadTrailerData() {
        viewModelScope.launch {
            _state.value = TrailerState.Loading

            movieDetailRepository.getMovieDetail(movieId).fold(
                onSuccess = { detail ->
                    _state.value = TrailerState.Success(detail)
                },
                onFailure = { exception ->
                    _state.value = TrailerState.Error(
                        exception.message ?: "Failed to load trailer details"
                    )
                }
            )
        }
    }
}
