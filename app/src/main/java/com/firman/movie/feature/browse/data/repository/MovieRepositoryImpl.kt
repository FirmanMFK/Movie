package com.firman.movie.feature.browse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.firman.movie.feature.browse.data.paging.MoviePagingSource
import com.firman.movie.feature.browse.data.remote.MovieApiService
import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.browse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val apiService: MovieApiService
) : MovieRepository {

    override fun getMoviesByGenre(genreId: Int, sortBy: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    apiService = apiService,
                    genreId = genreId,
                    sortBy = sortBy
                )
            }
        ).flow
    }
}
