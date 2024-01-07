package dev.baharudin.tmdb_android.presentation.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.core.domain.entities.Resource
import dev.baharudin.tmdb_android.core.domain.usecases.GetMovieGenres
import dev.baharudin.tmdb_android.presentation.common.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieGenres: GetMovieGenres,
) : ViewModel() {

    companion object {
        private const val TAG = "GenreListViewModel"
    }

    private val _genreList = MutableLiveData(DataState<List<Genre>>())

    val genreList: LiveData<DataState<List<Genre>>> = _genreList

    init {
        fetchMovieGenre()
    }

    private fun fetchMovieGenre() {
        Log.d(TAG, "fetchMovieGenre: started...")
        if (_genreList.value?.isLoading != false) {
            return
        }


        Log.d(TAG, "fetchMovieGenre: getMovieGenres use case started...")
        getMovieGenres().onEach { resource ->
            Log.d(TAG, "fetchMovieGenre: getMovieGenres resource -> $resource")
            when (resource) {
                is Resource.Success -> {
                    _genreList.postValue(DataState(data = resource.data))
                }

                is Resource.Error -> {
                    _genreList.postValue(DataState(errorMessage = resource.message))
                }

                is Resource.Loading -> {
                    _genreList.postValue(DataState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
        Log.d(TAG, "fetchMovieGenre: getMovieGenres finished!")
    }
}