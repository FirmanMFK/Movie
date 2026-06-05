package com.firman.movie.feature.browse.data.mapper

import com.firman.movie.core.common.extension.orZero
import com.firman.movie.feature.browse.data.remote.GenreDto
import com.firman.movie.feature.browse.domain.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id.orZero(),
        name = name.orEmpty()
    )
}

fun List<GenreDto>.toDomainList(): List<Genre> {
    return map { it.toDomain() }
}
