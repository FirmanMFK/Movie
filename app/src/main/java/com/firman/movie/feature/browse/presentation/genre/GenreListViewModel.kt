package com.firman.movie.feature.browse.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firman.movie.core.common.Resource
import com.firman.movie.feature.browse.domain.usecase.GetGenresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GenreListViewModel(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<GenreListState>(GenreListState.Loading)
    val state: StateFlow<GenreListState> = _state.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {
        viewModelScope.launch {
            _state.value = GenreListState.Loading
            when (val result = getGenresUseCase()) {
                is Resource.Success -> {
                    _state.value = GenreListState.Success(result.data)
                }
                is Resource.Error -> {
                    _state.value = GenreListState.Error(result.message)
                }
                is Resource.Loading -> {
                    _state.value = GenreListState.Loading
                }
            }
        }
    }
}
