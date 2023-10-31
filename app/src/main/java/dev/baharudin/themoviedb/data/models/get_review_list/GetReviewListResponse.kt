package dev.baharudin.themoviedb.data.models.get_review_list

import com.google.gson.annotations.SerializedName

data class GetReviewListResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var results: ArrayList<ReviewResponse> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)