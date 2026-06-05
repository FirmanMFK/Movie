package com.firman.movie.feature.browse.domain.usecase

import androidx.paging.PagingData
import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.browse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenreUseCase(
    private val repository: MovieRepository
) {
    operator fun invoke(genreId: Int, sortBy: String = "popularity.desc"): Flow<PagingData<Movie>> {
        return repository.getMoviesByGenre(genreId, sortBy)
    }
}
