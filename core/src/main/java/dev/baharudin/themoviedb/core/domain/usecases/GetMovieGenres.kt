package dev.baharudin.themoviedb.core.domain.usecases

import dev.baharudin.themoviedb.core.domain.entities.Genre
import dev.baharudin.themoviedb.core.domain.entities.Resource
import dev.baharudin.themoviedb.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieGenres @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> = flow {
        try {
            emit(Resource.Loading())
            val result = movieRepository.getMovieGenres()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}