package dev.baharudin.tmdb_android.core.domain.usecases

import androidx.paging.PagingData
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(genre: Genre): Flow<PagingData<Movie>> =
        movieRepository.discoverMoviesByGenre(genre)
}