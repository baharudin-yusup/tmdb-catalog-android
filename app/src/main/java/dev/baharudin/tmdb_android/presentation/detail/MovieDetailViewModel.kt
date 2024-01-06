package dev.baharudin.tmdb_android.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baharudin.tmdb_android.core.domain.entities.Resource
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.domain.usecases.AddToFavoriteMovie
import dev.baharudin.tmdb_android.core.domain.usecases.GetMovieDetail
import dev.baharudin.tmdb_android.core.domain.usecases.RemoveFromFavoriteMovie
import dev.baharudin.tmdb_android.presentation.common.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addToFavoriteMovie: AddToFavoriteMovie,
    private val removeFromFavoriteMovie: RemoveFromFavoriteMovie,
    private val getMovieDetail: GetMovieDetail,
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])
    private val _movie = MutableLiveData(DataState<Movie>(isLoading = true))
    val movie: LiveData<DataState<Movie>> = _movie

    init {
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        getMovieDetail(movieId)
            .onEach { mapMovieDetail(it) }
            .launchIn(viewModelScope)
    }

    fun toggleFavoriteButton() {
        _movie.value?.data?.let {movie ->
            if (movie.isFavorite) {
                removeFromFavoriteMovie(movie)
                    .onEach { mapMovieDetail(it) }
                    .launchIn(viewModelScope)
            } else {
                addToFavoriteMovie(movie)
                    .onEach { mapMovieDetail(it) }
                    .launchIn(viewModelScope)
            }
        }
    }

    private fun mapMovieDetail(resource: Resource<Movie>) {
        when (resource) {
            is Resource.Success -> {
                _movie.value = DataState(data = resource.data)
            }

            is Resource.Error -> {
                _movie.value = DataState(data = _movie.value?.data, errorMessage = resource.message)
            }

            is Resource.Loading -> {
                _movie.value = DataState(data = _movie.value?.data, isLoading = true)
            }
        }
    }
}