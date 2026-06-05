package com.firman.movie.feature.detail.data.mapper

import android.util.Log
import com.firman.movie.core.common.extension.orZero
import com.firman.movie.feature.detail.data.remote.dto.MovieDetailDto
import com.firman.movie.feature.detail.data.remote.dto.ReviewDto
import com.firman.movie.feature.detail.domain.model.Cast
import com.firman.movie.feature.detail.domain.model.MovieDetail
import com.firman.movie.feature.detail.domain.model.Review

fun MovieDetailDto.toDomain(): MovieDetail {
    val castList = credits?.cast?.map {
        Cast(
            id = it.id.orZero(),
            name = it.name.orEmpty(),
            character = it.character.orEmpty(),
            profilePath = it.profilePath.orEmpty()
        )
    } ?: emptyList()

    val trailerKey = videos?.results?.firstOrNull { 
        it.site?.equals("YouTube", ignoreCase = true) == true && 
        it.type?.equals("Trailer", ignoreCase = true) == true 
    }?.key ?: videos?.results?.firstOrNull { 
        it.site?.equals("YouTube", ignoreCase = true) == true 
    }?.key.orEmpty()

    return MovieDetail(
        id = id.orZero(),
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage.orZero(),
        runtime = runtime.orZero(),
        genres = genres?.mapNotNull { it.name } ?: emptyList(),
        cast = castList,
        youtubeTrailerKey = trailerKey
    )
}

fun ReviewDto.toDomain(): Review {
    return Review(
        id = id.orEmpty(),
        author = author ?: "Anonymous",
        content = content.orEmpty(),
        avatarPath = authorDetails?.avatarPath.orEmpty(),
        rating = authorDetails?.rating.orZero(),
        createdAt = createdAt.orEmpty()
    )
}
