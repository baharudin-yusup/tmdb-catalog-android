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

class GetGenreById @Inject constructor(
    private val movieRepository: MovieRepository
) {
    companion object {
        private const val TAG = "(UC) GetGenreById"
    }

    operator fun invoke(genreId: Int): Flow<Resource<Genre>> = flow {
        try {
            Log.d(TAG, "invoke: calling get movie genre by id started...")
            emit(Resource.Loading())
            val result = movieRepository.getMovieGenreById(genreId)
            Log.d(TAG, "invoke: calling get movie genre by id success!")
            Log.d(TAG, "invoke: genres result = $result")
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}