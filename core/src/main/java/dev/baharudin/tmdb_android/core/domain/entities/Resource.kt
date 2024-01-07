package dev.baharudin.tmdb_android.core.domain.entities

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>() {
        override fun toString(): String {
            return "${Resource::class.simpleName} ${Success::class.simpleName} = $data"
        }
    }

    class Error<T>(val message: String) : Resource<T>() {
        override fun toString(): String {
            return "${Resource::class.simpleName} ${Error::class.simpleName} = $message"
        }
    }

    class Loading<T> : Resource<T>() {
        override fun toString(): String {
            return "${Resource::class.simpleName} ${Loading::class.simpleName}"
        }
    }
}