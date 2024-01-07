package dev.baharudin.tmdb_android.presentation.screens.movie_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.presentation.common.components.MovieCard
import dev.baharudin.tmdb_android.presentation.navigations.NavigationItem
import dev.baharudin.tmdb_android.presentation.navigations.buildRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(navController: NavController, viewModel: MovieListViewModel = hiltViewModel()) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val genre by viewModel.genre.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.movie_list_fragment_title,
                            genre.data?.name ?: ""
                        )
                    )
                }
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                MovieListContent(
                    movies = movies,
                    onMovieClick = { movie ->
                        val nextRoute = NavigationItem.MovieDetail.route.buildRoute(
                            mapOf(
                                "movieId" to movie.id
                            )
                        )
                        navController.navigate(nextRoute)
                    },
                    onRetry = { movies.retry() }
                )
            }
        }
    )
}

@Composable
fun MovieListContent(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit,
    onRetry: () -> Unit
) {
    val loadState = movies.loadState

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (loadState.refresh) {
            is LoadState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.please_try_again),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = { onRetry() }) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
            }

            is LoadState.Loading -> {
                CircularProgressIndicator()
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies.itemCount) { index ->
                        val movie = checkNotNull(movies[index])
                        MovieCard(movie = movie, onClick = { onMovieClick(movie) })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    // Preview content here
}
