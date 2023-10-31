package dev.baharudin.themoviedb.domain.repositories

import androidx.paging.PagingData
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.entities.Review
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getMovieGenres(): List<Genre>
    fun discoverMoviesByGenre(genre: Genre): Flow<PagingData<Movie>>
    suspend fun getMovieReviews(movie: Movie, page: Int = 1): List<Review>
}