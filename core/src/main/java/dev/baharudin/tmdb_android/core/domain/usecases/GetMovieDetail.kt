package dev.baharudin.tmdb_android.core.domain.usecases

import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.domain.entities.Resource
import dev.baharudin.tmdb_android.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movie: Movie): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val result = movieRepository.getMovieDetail(movie)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    operator fun invoke(movieId: Int): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val result = movieRepository.getMovieDetail(movieId)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}