package dev.baharudin.themoviedb.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.usecases.DiscoverMoviesByGenre

class MovieListViewModel @AssistedInject constructor(
    @Assisted private val genre: Genre,
    discoverMoviesByGenre: DiscoverMoviesByGenre,
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            factory: Factory,
            genre: Genre,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(genre) as T
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(genre: Genre): MovieListViewModel
    }

    val movies = discoverMoviesByGenre(genre).cachedIn(viewModelScope)
}