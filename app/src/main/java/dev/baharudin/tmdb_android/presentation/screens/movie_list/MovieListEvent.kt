package dev.baharudin.tmdb_android.presentation.screens.movie_list

sealed class MovieListEvent {
     data object Init : MovieListEvent()
}