package dev.baharudin.tmdb_android.core.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.baharudin.tmdb_android.core.R
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.presentation.common.utils.toImageUrl

@Composable
fun MovieCard(
    movie: Movie,
    onClick: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clickable(onClick = { onClick(movie) })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            AsyncImage(
                model = movie.posterPath.toImageUrl(),
                contentDescription = stringResource(R.string.movie_thumbnail),
                modifier = Modifier
                    .size(width = 113.3.dp, height = 170.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        bottom = 4.dp,
                    )
                    .weight(1f)
            ) {
                // Title
                Text(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                        ),
                    text = movie.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Overview
                Text(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                        ),
                    text = movie.overview,
                    maxLines = 3,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1.0f))

                // Genres
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(movie.genres) {
                        GenreCard(it) {/* TODO: Handle this */}
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMovieCard() {
    MovieCard(
        movie = Movie(
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
            releaseDate = null,
            video = false,
            voteAverage = null,
            voteCount = null,
            isFavorite = false,
        ),
        onClick = {
        }
    )
}
