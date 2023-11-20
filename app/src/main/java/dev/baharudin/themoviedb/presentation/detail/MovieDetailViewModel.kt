package dev.baharudin.themoviedb.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.baharudin.themoviedb.common.Resource
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.usecases.AddToFavoriteMovie
import dev.baharudin.themoviedb.domain.usecases.GetMovieDetail
import dev.baharudin.themoviedb.domain.usecases.RemoveFromFavoriteMovie
import dev.baharudin.themoviedb.presentation.common.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieDetailViewModel @AssistedInject constructor(
    @Assisted private val initialMovie: Movie,
    private val addToFavoriteMovie: AddToFavoriteMovie,
    private val removeFromFavoriteMovie: RemoveFromFavoriteMovie,
    private val getMovieDetail: GetMovieDetail,
) : ViewModel() {
    @Suppress("UNCHECKED_CAST")
    companion object {
        private const val TAG = "MovieDetailViewModel"
        fun providesFactory(
            factory: Factory,
            movie: Movie,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(movie) as T
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(movie: Movie): MovieDetailViewModel
    }

    private val _movie = MutableLiveData(DataState(data = initialMovie, isLoading = true))
    val movie: LiveData<DataState<Movie>> = _movie

    init {
        getMovieDetail(initialMovie)
            .onEach { mapMovieDetail(it) }
            .launchIn(viewModelScope)
    }

    fun toggleFavoriteButton() {
        val movie = _movie.value?.data ?: initialMovie
        if (movie.isFavorite) {
            Log.d(TAG, "toggleFavoriteButton: remove from favorite started...")
            removeFromFavoriteMovie(movie)
                .onEach { mapMovieDetail(it) }
                .launchIn(viewModelScope)
        } else {
            Log.d(TAG, "toggleFavoriteButton: add to favorite started...")
            addToFavoriteMovie(movie)
                .onEach { mapMovieDetail(it) }
                .launchIn(viewModelScope)
        }
    }

    private fun mapMovieDetail(resource: Resource<Movie>) {
        when (resource) {
            is Resource.Success -> {
                Log.d(TAG, "mapMovieDetail: map movie detail success!")
                _movie.value = DataState(data = resource.data)
            }

            is Resource.Error -> {
                Log.d(TAG, "mapMovieDetail: map movie detail error = ${resource.message}")
                _movie.value = DataState(data = _movie.value?.data, errorMessage = resource.message)
            }

            is Resource.Loading -> {
                _movie.value = DataState(data = _movie.value?.data, isLoading = true)
            }
        }
    }
}