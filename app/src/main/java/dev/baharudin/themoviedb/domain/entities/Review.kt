package dev.baharudin.themoviedb.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val author: String,
    var content: String,
    var createdAt: String,
    var id: String,
    var updatedAt: String,
    var url: String,
): Parcelable