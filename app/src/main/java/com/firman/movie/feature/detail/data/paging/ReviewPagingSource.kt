package com.firman.movie.feature.detail.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.firman.movie.feature.browse.data.remote.MovieApiService
import com.firman.movie.feature.detail.data.mapper.toDomain
import com.firman.movie.feature.detail.domain.model.Review
import java.io.IOException

class ReviewPagingSource(
    private val apiService: MovieApiService,
    private val movieId: Int
) : PagingSource<Int, Review>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getMovieReviews(movieId = movieId, page = page)
            val reviews = response.results?.map { it.toDomain() } ?: emptyList()

            LoadResult.Page(
                data = reviews,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < (response.totalPages ?: 0)) page + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(Exception("No internet connection. Please check your network and try again."))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
