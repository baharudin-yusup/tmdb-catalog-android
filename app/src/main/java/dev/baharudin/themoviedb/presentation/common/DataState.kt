package dev.baharudin.themoviedb.presentation.common

data class DataState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

