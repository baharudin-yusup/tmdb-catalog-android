package dev.baharudin.themoviedb.presentation.home.favorite_movie_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import dev.baharudin.themoviedb.databinding.ItemMovieCardBinding
import dev.baharudin.themoviedb.databinding.ItemTitleBinding
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.presentation.movie_list.MovieGenreListAdapter

class FavoriteMovieListAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val onClick: (Movie) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_TITLE = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    inner class MovieListViewHolder(var binding: ItemMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                tvMovieTitle.text = movie.title
                tvMovieStoryline.text = movie.overview
                setThumbnail(movie.posterPath, ivMovieThumbnail)
                root.setOnClickListener { onClick(movie) }
            }
            movie.genres?.let {
                setupGenreList(it)
            }
        }

        private fun setThumbnail(path: String, imageView: ImageView) {
            val drawable = CircularProgressDrawable(context)
            drawable.centerRadius = 30f
            drawable.strokeWidth = 6f
            drawable.start()
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500$path")
                .timeout(25000)
                .centerCrop()
                .placeholder(drawable)
                .into(imageView)
        }

        private fun setupGenreList(genres: List<Genre>) {
            val movieGenreListAdapter = MovieGenreListAdapter(genres)
            val linearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvGenres.apply {
                adapter = movieGenreListAdapter
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = movieGenreListAdapter
            }
        }
    }

    inner class TitleViewHolder(var binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvTitle.text =
                if (movies.size > 1) "Favorite Movies" else "Favorite Movie"
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_TITLE else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_TITLE -> {
                val binding =
                    ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding)
            }

            else -> {
                val binding =
                    ItemMovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieListViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_TITLE -> {
                (holder as TitleViewHolder).bind()
            }

            else -> {
                (holder as MovieListViewHolder).bind(movies[position - 1])
            }
        }
    }

    override fun getItemCount(): Int = movies.size + 1
}