package dev.baharudin.tmdb_android.core.domain.usecases

import android.util.Log
import androidx.paging.PagingData
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiscoverMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {

    companion object {
        const val TAG = "(UC) DiscoverMovies"
    }
    operator fun invoke(genre: Genre): Flow<PagingData<Movie>> {
        Log.d(TAG, "invoke: get movies by $genre")
        return movieRepository.discoverMoviesByGenre(genre);
    }

    operator fun invoke(genreName: String): Flow<PagingData<Movie>> {
        Log.d(TAG, "invoke: get movies by $genreName")
        return movieRepository.discoverMoviesByGenre(genreName);
    }
}