package com.firman.movie.feature.browse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.firman.movie.feature.browse.data.mapper.toDomain
import com.firman.movie.feature.browse.data.remote.MovieApiService
import com.firman.movie.feature.browse.domain.model.Movie
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.time.Duration.Companion.milliseconds

class SearchMoviePagingSource(
    private val apiService: MovieApiService,
    private val query: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            if (query.isBlank()) {
                return LoadResult.Page(emptyList(), null, null)
            }
            // Small delay to show loading state if desired, or skip it
            delay(500.milliseconds)
            val response = apiService.searchMovies(
                query = query,
                page = page
            )
            val movies = response.results?.map { it.toDomain() } ?: emptyList()
            val totalPages = response.totalPages ?: 1

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(Exception("No internet connection. Please check your network and try again."))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
