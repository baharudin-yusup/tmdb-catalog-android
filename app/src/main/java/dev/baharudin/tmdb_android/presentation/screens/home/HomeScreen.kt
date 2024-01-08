package dev.baharudin.tmdb_android.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.presentation.common.components.GenreCard
import dev.baharudin.tmdb_android.core.presentation.common.utils.showErrorMessage
import dev.baharudin.tmdb_android.presentation.navigations.NavigationItem
import dev.baharudin.tmdb_android.presentation.navigations.buildRoute

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val genres by homeViewModel.genreList.observeAsState()

    if (!genres?.errorMessage.isNullOrBlank()) {
        genres?.errorMessage?.showErrorMessage(LocalContext.current)
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.home_fragment_title)) },
            actions = {
                IconButton(onClick = { /* TODO: Handle about button click */ }) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = null)
                }
            })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            val route = NavigationItem.MovieList.route.buildRoute(
                mapOf("onlyFavorite" to true)
            )
            Log.d("HomeScreen", "navigate to $route")
            navController.navigate(route)
        },
            content = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null) })
    }, content = { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            FlowRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                genres?.data?.forEach {
                    GenreCard(it) { genre ->
                        val route = NavigationItem.MovieList.route.buildRoute(
                            mapOf(
                                "genreId" to genre.id, "genreName" to genre.name
                            )
                        )
                        Log.d("HomeScreen", "navigate to $route")
                        navController.navigate(route)
                    }
                }
            }

            if (genres?.isLoading == true) {
                Box(
                    modifier = Modifier.align(Alignment.Center),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                    )
                }
            }
        }
    })
}
