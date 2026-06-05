package com.firman.movie.feature.browse.domain.usecase

import com.firman.movie.core.common.Resource
import com.firman.movie.feature.browse.domain.model.Genre
import com.firman.movie.feature.browse.domain.repository.GenreRepository

class GetGenresUseCase(
    private val repository: GenreRepository
) {
    suspend operator fun invoke(): Resource<List<Genre>> {
        return repository.getGenres()
    }
}
