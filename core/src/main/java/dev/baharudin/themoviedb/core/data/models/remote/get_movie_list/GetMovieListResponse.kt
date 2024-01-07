package dev.baharudin.themoviedb.core.data.models.remote.get_movie_list

import com.google.gson.annotations.SerializedName

data class GetMovieListResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: ArrayList<MovieResponse> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
)