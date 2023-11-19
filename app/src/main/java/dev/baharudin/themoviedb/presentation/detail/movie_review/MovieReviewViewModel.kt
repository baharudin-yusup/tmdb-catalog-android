package dev.baharudin.themoviedb.presentation.detail.movie_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.domain.usecases.GetMovieReviews

class MovieReviewViewModel @AssistedInject constructor(
    @Assisted private val movie: Movie,
    getMovieReviews: GetMovieReviews,
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    companion object {
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
        fun create(movie: Movie): MovieReviewViewModel
    }

    val reviews = getMovieReviews(movie).cachedIn(viewModelScope)
}