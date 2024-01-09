package dev.baharudin.tmdb_android.presentation.screens.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.R as CoreR
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.presentation.common.utils.toImageUrl

@Composable
fun MovieDetailHeader(
    movie: Movie,
    onFavoriteClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Poster
            SubcomposeAsyncImage(
                model = movie.posterPath.toImageUrl(),
                contentDescription = stringResource(CoreR.string.movie_thumbnail),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )

            // Small Info
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
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

                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
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
            }

            // Icons
            Column(
                modifier = Modifier.
                fillMaxHeight()
            ) {
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
}

@Preview
@Composable
fun PreviewMovieDetailHeader() {
    val movie = Movie(
        title = "Movie Dummy 1",
        overview = "Jetpack Compose is interoperable with the View system, and the degree of migration can be decided depending on the project. It is possible to choose what to write with Compose: a full app, the content of one Fragment, or an UI element.",
        posterPath = "/ui4DrH1cKk2vkHshcUcGt2lKxCm.jpg",
        backdropPath = "/ui4DrH1cKk2vkHshcUcGt2lKxCm.jpg",
        genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Drama"),
            Genre(2, "Supernatural"),
            Genre(2, "Romance")
        ),
        id = 1,
        originalLanguage = null,
        originalTitle = null,
        popularity = null,
        releaseDate = "2022-01-1",
        video = false,
        voteAverage = null,
        voteCount = null,
        isFavorite = false,
    )

    MovieDetailHeader(movie = movie) {

    }
}