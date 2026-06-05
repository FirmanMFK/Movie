package com.firman.movie.feature.browse.data.repository

import com.firman.movie.core.common.Resource
import com.firman.movie.feature.browse.data.mapper.toDomainList
import com.firman.movie.feature.browse.data.remote.MovieApiService
import com.firman.movie.feature.browse.domain.model.Genre
import com.firman.movie.feature.browse.domain.repository.GenreRepository
import java.io.IOException

class GenreRepositoryImpl(
    private val apiService: MovieApiService
) : GenreRepository {

    override suspend fun getGenres(): Resource<List<Genre>> {
        return try {
            val response = apiService.getMovieGenres()
            val genres = response.genres?.toDomainList() ?: emptyList()
            Resource.Success(genres)
        } catch (e: IOException) {
            Resource.Error("No internet connection. Please check your network and try again.")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unexpected error occurred")
        }
    }
}
