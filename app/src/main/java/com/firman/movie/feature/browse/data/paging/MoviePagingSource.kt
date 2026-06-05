package com.firman.movie.feature.browse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.firman.movie.feature.browse.data.mapper.toDomain
import com.firman.movie.feature.browse.data.remote.MovieApiService
import com.firman.movie.feature.browse.domain.model.Movie
import kotlinx.coroutines.delay
import java.io.IOException
import kotlin.time.Duration.Companion.milliseconds

class MoviePagingSource(
    private val apiService: MovieApiService,
    private val genreId: Int,
    private val sortBy: String
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
            delay(1000.milliseconds)
            val response = apiService.discoverMovies(
                genreId = genreId,
                page = page,
                sortBy = sortBy
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
