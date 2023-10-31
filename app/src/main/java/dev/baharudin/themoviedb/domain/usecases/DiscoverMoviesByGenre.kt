package dev.baharudin.themoviedb.domain.usecases

import androidx.paging.PagingData
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMoviesByGenre @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(genre: Genre): Flow<PagingData<Movie>> =
        movieRepository.discoverMoviesByGenre(genre)
}