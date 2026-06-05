package com.firman.movie.feature.detail.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    @SerialName("id") val id: Int?,
    @SerialName("title") val title: String?,
    @SerialName("overview") val overview: String?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("genres") val genres: List<GenreDto>? = null,
    @SerialName("credits") val credits: CreditsDto? = null,
    @SerialName("videos") val videos: VideosDto? = null
)

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?
)

@Serializable
data class CreditsDto(
    @SerialName("cast") val cast: List<CastDto>?
)

@Serializable
data class CastDto(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("character") val character: String?,
    @SerialName("profile_path") val profilePath: String?
)

@Serializable
data class VideosDto(
    @SerialName("results") val results: List<VideoDto>?
)

@Serializable
data class VideoDto(
    @SerialName("id") val id: String?,
    @SerialName("key") val key: String?,
    @SerialName("name") val name: String?,
    @SerialName("site") val site: String?,
    @SerialName("type") val type: String?
)
