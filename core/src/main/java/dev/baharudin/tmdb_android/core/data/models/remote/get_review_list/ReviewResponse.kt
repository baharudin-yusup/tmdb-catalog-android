package dev.baharudin.tmdb_android.core.data.models.remote.get_review_list

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("author") var author: String,
    @SerializedName("author_details") var authorDetails: AuthorDetailResponse,
    @SerializedName("content") var content: String,
    @SerializedName("created_at") var createdAt: String,
    @SerializedName("id") var id: String,
    @SerializedName("updated_at") var updatedAt: String,
    @SerializedName("url") var url: String
)