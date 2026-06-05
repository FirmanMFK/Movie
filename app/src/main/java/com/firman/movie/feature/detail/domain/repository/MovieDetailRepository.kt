package com.firman.movie.feature.detail.domain.repository

import androidx.paging.PagingData
import com.firman.movie.feature.detail.domain.model.MovieDetail
import com.firman.movie.feature.detail.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
}
