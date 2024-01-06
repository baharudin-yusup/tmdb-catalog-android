package dev.baharudin.tmdb_android.presentation.movie_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.domain.entities.Resource
import dev.baharudin.tmdb_android.core.domain.usecases.DiscoverMovies
import dev.baharudin.tmdb_android.core.domain.usecases.GetGenreById
import dev.baharudin.tmdb_android.presentation.common.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val discoverMovies: DiscoverMovies,
    private val getGenreById: GetGenreById,
) : ViewModel() {
    private val genreId: Int = checkNotNull(savedStateHandle.get<Int>("genreId"))

    private val _genre = MutableStateFlow<DataState<Genre>>(DataState())
    val genre: StateFlow<DataState<Genre>> get() = _genre

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: StateFlow<PagingData<Movie>>
        get() = _genre
            .filter { it.data != null }
            .distinctUntilChanged()
            .map { it.data!! }
            .flatMapLatest { genre ->
                discoverMovies(genre)
            }.cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    init {
        getGenre()
    }

    private fun getGenre() {
        getGenreById(genreId).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    _genre.value = DataState(errorMessage = resource.message)
                }

                is Resource.Loading -> {
                    _genre.value = DataState(isLoading = true)
                }

                is Resource.Success -> {
                    _genre.value = DataState(data = resource.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}