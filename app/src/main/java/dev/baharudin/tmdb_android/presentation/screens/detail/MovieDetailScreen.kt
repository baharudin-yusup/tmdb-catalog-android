package dev.baharudin.tmdb_android.presentation.screens.detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import dev.baharudin.tmdb_android.presentation.navigations.NavigationItem
import dev.baharudin.tmdb_android.presentation.navigations.buildRoute
import dev.baharudin.tmdb_android.presentation.screens.detail.components.MovieDetailHeader
import dev.baharudin.tmdb_android.presentation.screens.detail.components.MovieDetailInfo
import dev.baharudin.tmdb_android.presentation.screens.detail.components.MovieReview

@Composable
fun MovieDetailScreen(
    navController: NavHostController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movie by viewModel.movie.observeAsState()
    val reviews = viewModel.reviews.collectAsLazyPagingItems()
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            movie?.data?.let { movie ->
                Column {
                    MovieDetailHeader(movie) { viewModel.toggleFavoriteButton() }

                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        listOf("Details", "Reviews").forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(text = title) }
                            )
                        }
                    }

                    // Content based on selected tab
                    Box(modifier = Modifier.fillMaxHeight()) {
                        when (selectedTabIndex) {
                            0 -> {
                                MovieDetailInfo(movie = movie) { genre ->
                                    val route = NavigationItem.MovieList.route.buildRoute(
                                        mapOf(
                                            "genreId" to genre.id, "genreName" to genre.name
                                        )
                                    )
                                    Log.d("MovieDetailScreen", "navigate to $route")
                                    navController.navigate(route)
                                }
                            }

                            1 -> {
                                MovieReview(reviews = reviews)
                            }
                        }
                    }
                }
            }
        }
    }
}



