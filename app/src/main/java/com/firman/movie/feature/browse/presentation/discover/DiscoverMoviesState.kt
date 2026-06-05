package com.firman.movie.feature.browse.presentation.discover

enum class SortFilter(val displayName: String, val apiValue: String) {
    Popular("Popular", "popularity.desc"),
    TopRated("Top Rated", "vote_average.desc"),
    NewReleases("New Releases", "primary_release_date.desc")
}

data class DiscoverMoviesState(
    val selectedSort: SortFilter = SortFilter.Popular
)
