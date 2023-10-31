package dev.baharudin.themoviedb.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.baharudin.themoviedb.data.sources.MovieListSource
import dev.baharudin.themoviedb.data.sources.api.TheMovieDBApi
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.entities.Review
import dev.baharudin.themoviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val theMovieDBApi: TheMovieDBApi,
) : MovieRepository {

    override suspend fun getMovieGenres(): List<Genre> {
        val response = theMovieDBApi.getGenreList()
        return response.genres.map { it.toEntity() }
    }

    override fun discoverMoviesByGenre(genre: Genre): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MovieListSource(
                    theMovieDBApi, genre
                )
            }
        ).flow.map { pagingData -> pagingData.map { it.toEntity() } }
    }

    override suspend fun getMovieReviews(movie: Movie, page: Int): List<Review> {
        val response = theMovieDBApi.getMovieReviews(movieId = movie.id, page = page)
        return response.results.map { it.toEntity() }
    }
}