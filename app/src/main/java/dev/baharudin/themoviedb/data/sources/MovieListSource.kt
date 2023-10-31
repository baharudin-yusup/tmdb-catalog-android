package dev.baharudin.themoviedb.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.baharudin.themoviedb.data.models.get_movie_list.MovieResponse
import dev.baharudin.themoviedb.data.sources.api.TheMovieDBApi
import dev.baharudin.themoviedb.domain.entities.Genre
import retrofit2.HttpException
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1

class MovieListSource(
    private val theMovieDBApi: TheMovieDBApi,
    private val genre: Genre,
) : PagingSource<Int, MovieResponse>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = theMovieDBApi.getMovieList(
                withGenres = genre.name,
                page = pageIndex
            )
            val movies = response.results
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(
                Exception(
                    e.localizedMessage ?: "Couldn't reach server. Check your internet connection."
                )
            )
        } catch (e: HttpException) {
            return LoadResult.Error(
                Exception(e.message())
            )
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
