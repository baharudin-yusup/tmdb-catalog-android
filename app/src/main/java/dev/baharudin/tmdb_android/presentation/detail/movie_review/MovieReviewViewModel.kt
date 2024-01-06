package dev.baharudin.tmdb_android.presentation.detail.movie_review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baharudin.tmdb_android.core.domain.entities.Movie
import dev.baharudin.tmdb_android.core.domain.usecases.GetMovieReviews
import javax.inject.Inject

@HiltViewModel
class MovieReviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieReviews: GetMovieReviews,
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    val reviews = getMovieReviews(movieId).cachedIn(viewModelScope)
}