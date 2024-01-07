package dev.baharudin.themoviedb.core.domain.usecases

import androidx.paging.PagingData
import dev.baharudin.themoviedb.core.domain.entities.Genre
import dev.baharudin.themoviedb.core.domain.entities.Movie
import dev.baharudin.themoviedb.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesByGenre @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(genre: Genre): Flow<PagingData<Movie>> =
        movieRepository.discoverMoviesByGenre(genre)
}