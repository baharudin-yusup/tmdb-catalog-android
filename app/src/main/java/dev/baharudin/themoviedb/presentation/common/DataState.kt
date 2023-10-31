package dev.baharudin.themoviedb.presentation.common

data class DataState<T> (
    val data: T? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

data class DataPaginationState<T> (
    val data: ArrayList<T> = arrayListOf(),
    val isLoading: Boolean = false,
    val currentPage: Int = 0,
    val message: String = ""
)