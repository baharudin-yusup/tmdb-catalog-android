package dev.baharudin.themoviedb.core.domain.repositories

import androidx.paging.PagingData
import dev.baharudin.themoviedb.core.domain.entities.Genre
import dev.baharudin.themoviedb.core.domain.entities.Movie
import dev.baharudin.themoviedb.core.domain.entities.Review
import kotlinx.coroutines.flow.Flow


interface MovieRepository {
    suspend fun getMovieGenres(): List<Genre>
    suspend fun getMovieDetail(movie: Movie): Movie
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun discoverMoviesByGenre(genre: Genre): Flow<PagingData<Movie>>
    fun getMovieReviews(movie: Movie): Flow<PagingData<Review>>
    fun addToFavoriteMovie(movie: Movie): Movie
    fun removeFromFavoriteMovie(movie: Movie): Movie
}