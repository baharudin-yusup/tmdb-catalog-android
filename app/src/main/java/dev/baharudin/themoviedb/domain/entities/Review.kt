package dev.baharudin.themoviedb.domain.entities

data class Review(
    val author: String,
    var content: String,
    var createdAt: String,
    var id: String,
    var updatedAt: String,
    var url: String,
)