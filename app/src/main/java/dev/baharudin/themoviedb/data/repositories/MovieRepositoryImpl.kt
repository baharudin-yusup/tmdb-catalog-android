package dev.baharudin.themoviedb.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.baharudin.themoviedb.data.models.toDbEntity
import dev.baharudin.themoviedb.data.models.toEntity
import dev.baharudin.themoviedb.data.sources.MovieListSource
import dev.baharudin.themoviedb.data.sources.ReviewListSource
import dev.baharudin.themoviedb.data.sources.local.db.GenreDao
import dev.baharudin.themoviedb.data.sources.remote.TheMovieDBApi
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.entities.Review
import dev.baharudin.themoviedb.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val genreDao: GenreDao,
    private val theMovieDBApi: TheMovieDBApi,
) : MovieRepository {

    override suspend fun getMovieGenres(): List<Genre> {
        val response = theMovieDBApi.getGenreList()
        val genreResponseList = response.genres
        genreDao.insertAll(*genreResponseList.map { it.toDbEntity() }.toTypedArray())
        return genreResponseList.map { it.toEntity() }
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
        ).flow.map { pagingData ->
            pagingData.map {
                val genres =
                    genreDao.loadAllByIds(it.genreIds.toIntArray())
                        .map { data -> data.toEntity() }
                it.toEntity(genres)
            }
        }
    }

    override fun getMovieReviews(movie: Movie): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewListSource(
                    theMovieDBApi, movie
                )
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toEntity() }
        }
    }
}