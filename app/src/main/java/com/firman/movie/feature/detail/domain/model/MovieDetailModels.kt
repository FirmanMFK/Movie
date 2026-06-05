package com.firman.movie.feature.detail.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val runtime: Int,
    val genres: List<String>,
    val cast: List<Cast>,
    val youtubeTrailerKey: String
)

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String
)

data class Review(
    val id: String,
    val author: String,
    val content: String,
    val avatarPath: String,
    val rating: Double,
    val createdAt: String
)
