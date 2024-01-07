package dev.baharudin.tmdb_android.core.domain.usecases

import android.util.Log
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.core.domain.entities.Resource
import dev.baharudin.tmdb_android.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieGenres @Inject constructor(
    private val movieRepository: MovieRepository
) {
    companion object {
        private const val TAG = "GetMovieGenres"
    }

    operator fun invoke(): Flow<Resource<List<Genre>>> = flow {
        try {
            Log.d(TAG, "invoke: started...")
            emit(Resource.Loading())
            Log.d(TAG, "invoke: calling get movie genres started...")
            val result = movieRepository.getMovieGenres()
            Log.d(TAG, "invoke: calling get movie genres success!")
            Log.d(TAG, "invoke: genres result -> $result")
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}