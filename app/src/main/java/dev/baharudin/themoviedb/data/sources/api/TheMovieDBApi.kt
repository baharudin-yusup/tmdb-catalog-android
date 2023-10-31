package dev.baharudin.themoviedb.data.sources.api

import dev.baharudin.themoviedb.data.models.get_genre_list.GetGenreListResponse
import dev.baharudin.themoviedb.data.models.get_movie_list.GetMovieListResponse
import dev.baharudin.themoviedb.data.models.get_review_list.GetReviewListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBApi {
    @GET("3/genre/movie/list")
    suspend fun getGenreList(): GetGenreListResponse

    @GET("3/discover/movie")
    suspend fun getMovieList(
        @Query("with_genres", encoded = true) withGenres: String,
        @Query("page") page: Int = 1,
        @Query("language") location: String = "en-US",
    ): GetMovieListResponse

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Query("movie_id", encoded = true) movieId: Int,
        @Query("page") page: Int = 1,
    ): GetReviewListResponse
}