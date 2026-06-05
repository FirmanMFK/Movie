package com.firman.movie.feature.browse.data.mapper

import com.firman.movie.core.common.extension.orZero
import com.firman.movie.feature.browse.data.remote.MovieDto
import com.firman.movie.feature.browse.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id.orZero(),
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        voteAverage = voteAverage.orZero(),
        releaseDate = releaseDate.orEmpty()
    )
}

fun List<MovieDto>.toDomainList(): List<Movie> {
    return map { it.toDomain() }
}
