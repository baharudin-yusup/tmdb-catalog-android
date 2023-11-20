package dev.baharudin.themoviedb.domain.usecases

import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> =
        movieRepository.getFavoriteMovies()
}