package com.firman.movie.feature.browse.domain.repository

import androidx.paging.PagingData
import com.firman.movie.feature.browse.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesByGenre(genreId: Int, sortBy: String): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
}
