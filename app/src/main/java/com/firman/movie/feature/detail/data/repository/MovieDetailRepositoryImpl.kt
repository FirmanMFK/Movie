package com.firman.movie.feature.detail.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.firman.movie.feature.browse.data.remote.MovieApiService
import com.firman.movie.feature.detail.data.mapper.toDomain
import com.firman.movie.feature.detail.data.paging.ReviewPagingSource
import com.firman.movie.feature.detail.domain.model.MovieDetail
import com.firman.movie.feature.detail.domain.model.Review
import com.firman.movie.feature.detail.domain.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class MovieDetailRepositoryImpl(
    private val apiService: MovieApiService
) : MovieDetailRepository {

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return try {
            val response = apiService.getMovieDetails(movieId = movieId)
            Log.d("TrailerMapKey" , " ${response.videos?.results}")
            Result.success(response.toDomain())
        } catch (e: IOException) {
            Result.failure(Exception("No internet connection. Please check your network and try again."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getMovieReviews(movieId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            pagingSourceFactory = { ReviewPagingSource(apiService, movieId) }
        ).flow
    }
}
