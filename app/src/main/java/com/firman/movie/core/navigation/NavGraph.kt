package com.firman.movie.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.firman.movie.feature.browse.presentation.discover.DiscoverMoviesScreen
import com.firman.movie.feature.browse.presentation.discover.DiscoverMoviesViewModel
import com.firman.movie.feature.browse.presentation.genre.GenreListScreen
import com.firman.movie.feature.detail.presentation.MovieDetailScreen
import com.firman.movie.feature.detail.presentation.MovieDetailViewModel
import com.firman.movie.feature.detail.presentation.trailer.TrailerScreen
import com.firman.movie.feature.detail.presentation.trailer.TrailerViewModel
import com.firman.movie.feature.watchlist.presentation.WatchlistScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.GenreList
    ) {
        composable<Screen.GenreList> {
            GenreListScreen(
                onGenreClick = { genre ->
                    navController.navigate(
                        Screen.DiscoverMovies(
                            genreId = genre.id,
                            genreName = genre.name
                        )
                    )
                },
                paddingValues = paddingValues,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }

        composable<Screen.DiscoverMovies> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.DiscoverMovies>()
            val viewModel: DiscoverMoviesViewModel = koinViewModel {
                parametersOf(route.genreId, route.genreName)
            }
            DiscoverMoviesScreen(
                viewModel = viewModel,
                paddingValues = paddingValues,
                onBackClick = { navController.popBackStack() },
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail(movieId = movieId))
                }
            )
        }

        composable<Screen.Watchlist> {
            WatchlistScreen(
                paddingValues = paddingValues,
                onMovieClick = { movieId ->
                    navController.navigate(Screen.MovieDetail(movieId = movieId))
                }
            )
        }

        composable<Screen.MovieDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.MovieDetail>()
            val viewModel: MovieDetailViewModel = koinViewModel {
                parametersOf(route.movieId)
            }
            MovieDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onWatchTrailerClick = { movieId ->
                    navController.navigate(Screen.Trailer(movieId = movieId))
                }
            )
        }

        composable<Screen.Trailer> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.Trailer>()
            val viewModel: TrailerViewModel =
                koinViewModel {
                    parametersOf(route.movieId)
                }
            TrailerScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
