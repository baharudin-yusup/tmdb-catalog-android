package dev.baharudin.tmdb_android.core.domain.usecases

import android.util.Log
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

    companion object {
        const val TAG = "(UC) GetMovieDetail"
    }
    operator fun invoke(movie: Movie): Flow<Resource<Movie>> = flow {
        Log.d(TAG, "invoke: started...")
        try {
            emit(Resource.Loading())
            val result = movieRepository.getMovieDetail(movie)
            Log.d(TAG, "invoke: success!")
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            Log.e(TAG, "invoke: error!", e)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(TAG, "invoke: error!", e)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            Log.e(TAG, "invoke: error!", e)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    operator fun invoke(movieId: Int): Flow<Resource<Movie>> = flow {
        Log.d(TAG, "invoke: started...")
        try {
            emit(Resource.Loading())
            val result = movieRepository.getMovieDetail(movieId)
            Log.d(TAG, "invoke: success!")
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            Log.e(TAG, "invoke: error!", e)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            Log.e(TAG, "invoke: error!", e)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            Log.e(TAG, "invoke: error!", e)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}