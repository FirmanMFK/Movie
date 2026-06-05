package com.firman.movie.feature.browse.domain.repository

import com.firman.movie.core.common.Resource
import com.firman.movie.feature.browse.domain.model.Genre

interface GenreRepository {
    suspend fun getGenres(): Resource<List<Genre>>
}
