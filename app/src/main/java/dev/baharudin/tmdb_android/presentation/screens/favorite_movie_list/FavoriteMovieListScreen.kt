package dev.baharudin.tmdb_android.presentation.screens.favorite_movie_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.presentation.common.components.MovieCard
import dev.baharudin.tmdb_android.presentation.navigations.NavigationItem
import dev.baharudin.tmdb_android.presentation.navigations.buildRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMovieListScreen(
    navController: NavController,
    viewModel: FavoriteMovieListViewModel = hiltViewModel()
) {
    val movies by viewModel.favoriteMovieStateFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.movie_list_fragment_title,
                            "Favorite"
                        )
                    )
                }
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                MovieListContent(
                    movies = movies.data ?: listOf(),
                    onMovieClick = { movie ->
                        val nextRoute = NavigationItem.MovieDetail.route.buildRoute(
                            mapOf(
                                "movieId" to movie.id
                            )
                        )
                        navController.navigate(nextRoute)
                    },
                )
            }
        }
    )
}

@Composable
fun MovieListContent(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies.size) { index ->
            val movie = movies[index]
            MovieCard(movie = movie, onClick = { onMovieClick(movie) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    // Preview content here
}
