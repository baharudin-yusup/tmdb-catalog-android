package dev.baharudin.tmdb_android.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.baharudin.tmdb_android.presentation.screens.about.AboutScreen
import dev.baharudin.tmdb_android.presentation.screens.detail.MovieDetailScreen
import dev.baharudin.tmdb_android.presentation.screens.favorite_movie_list.FavoriteMovieListScreen
import dev.baharudin.tmdb_android.presentation.screens.home.HomeScreen
import dev.baharudin.tmdb_android.presentation.screens.movie_list.MovieListScreen
import dev.baharudin.tmdb_android.presentation.screens.movie_list.MovieListViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }

        composable(NavigationItem.About.route) {
            AboutScreen()
        }


        composable(
            NavigationItem.MovieList.route,
            NavigationItem.MovieList.navArgument
        ) { backStackEntry ->
            val onlyFavorite: Boolean =
                checkNotNull(backStackEntry.arguments?.getBoolean("onlyFavorite"))

            if (onlyFavorite) {
                FavoriteMovieListScreen(navController = navController)
            } else {
                MovieListScreen(navController = navController)
            }
        }

        composable(NavigationItem.MovieDetail.route, NavigationItem.MovieDetail.navArgument) {
            MovieDetailScreen(navController = navController)
        }
    }
}