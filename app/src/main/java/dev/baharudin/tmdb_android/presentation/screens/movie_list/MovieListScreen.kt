package dev.baharudin.tmdb_android.presentation.screens.movie_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.presentation.common.components.MovieCard
import dev.baharudin.tmdb_android.core.presentation.common.components.RetrySection
import dev.baharudin.tmdb_android.presentation.navigations.NavigationItem
import dev.baharudin.tmdb_android.presentation.navigations.buildRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(navController: NavController, viewModel: MovieListViewModel = hiltViewModel()) {
    val movies = viewModel.moviesState.collectAsLazyPagingItems()
    val genre by viewModel.genreState.collectAsState()

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
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies.itemCount) { index ->
                    val movie = checkNotNull(movies[index])
                    MovieCard(movie = movie, onClick = { selectedMovie ->
                        val nextRoute = NavigationItem.MovieDetail.route.buildRoute(
                            mapOf(
                                "movieId" to selectedMovie.id
                            )
                        )
                        navController.navigate(nextRoute)
                    })
                }

                movies.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val state = movies.loadState.refresh as LoadState.Error
                            item {
                                RetrySection(
                                    errorMessage = state.error.localizedMessage,
                                    onRetryClick = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val state = movies.loadState.append as LoadState.Error
                            item {
                                RetrySection(
                                    errorMessage = state.error.localizedMessage,
                                    onRetryClick = { retry() })
                            }
                        }
                    }
                }
            }
        }
    )
}
