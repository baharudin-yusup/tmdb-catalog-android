package dev.baharudin.tmdb_android.core.domain.usecases

import androidx.paging.PagingData
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.domain.entities.Review
import dev.baharudin.tmdb_android.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviews @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movie: Movie): Flow<PagingData<Review>> =
        movieRepository.getMovieReviews(movie)

    operator fun invoke(movieId: Int): Flow<PagingData<Review>> =
        movieRepository.getMovieReviews(movieId)
}