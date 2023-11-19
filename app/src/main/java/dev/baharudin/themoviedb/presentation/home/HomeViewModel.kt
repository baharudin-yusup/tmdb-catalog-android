package dev.baharudin.themoviedb.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baharudin.themoviedb.common.Resource
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.usecases.GetMovieGenres
import dev.baharudin.themoviedb.presentation.common.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieGenres: GetMovieGenres,
) : ViewModel() {

    private val _genreList = MutableLiveData(DataState<List<Genre>>())

    val genreList: LiveData<DataState<List<Genre>>> = _genreList

    init {
        fetchMovieGenre()
    }

    fun fetchMovieGenre() {
        if (_genreList.value?.isLoading != false) {
            return
        }

        getMovieGenres().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    _genreList.value = DataState(data = resource.data)
                }

                is Resource.Error -> {
                    _genreList.value = DataState(errorMessage = resource.message)
                }

                is Resource.Loading -> {
                    _genreList.value = DataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}