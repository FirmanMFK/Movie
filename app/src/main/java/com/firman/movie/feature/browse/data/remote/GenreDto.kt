package com.firman.movie.feature.browse.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null
)

@Serializable
data class GenreListResponse(
    @SerialName("genres") val genres: List<GenreDto>? = null
)
