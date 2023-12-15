package dev.baharudin.themoviedb.core.domain.usecases

import androidx.paging.PagingData
import dev.baharudin.themoviedb.core.domain.entities.Movie
import dev.baharudin.themoviedb.core.domain.entities.Review
import dev.baharudin.themoviedb.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieReviews @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movie: Movie): Flow<PagingData<Review>> =
        movieRepository.getMovieReviews(movie)
}