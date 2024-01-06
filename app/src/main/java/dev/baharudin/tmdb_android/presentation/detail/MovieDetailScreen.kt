package dev.baharudin.tmdb_android.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.presentation.common.utils.toImageUrl

@Composable
fun MovieDetailScreen(
    navController: NavHostController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movie by viewModel.movie.observeAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        // Scaffold parameters...
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            movie?.data?.let { movie ->
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
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
                    when (selectedTabIndex) {
                        0 -> {
                            MovieInfoScreen(movie = movie)
                        }

                        1 -> {
                            MovieReviewScreen()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MovieDetailHeader(
    movie: Movie,
    onFavoriteClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(125.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = movie.posterPath.toImageUrl(),
                contentDescription = stringResource(R.string.movie_thumbnail),
                modifier = Modifier
                    .size(width = 83.dp, height = 83.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                movie.releaseDate?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: Add rating bar
//                RatingBar(
//                    value = 9.0f,
//                    numo = 10f,
//                    modifier = Modifier
//                        .padding(end = 4.dp)
//                        .size(24.dp)
//                )
//                Text(
//                    text = movieRating,
//                    style = MaterialTheme.typography.bodyMedium
//                )
            }

            IconButton(
                onClick = { onFavoriteClicked() },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = if (movie.isFavorite) R.drawable.ic_bookmark_on else R.drawable.ic_bookmark_off),
                    contentDescription = null,
                    tint = if (movie.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
