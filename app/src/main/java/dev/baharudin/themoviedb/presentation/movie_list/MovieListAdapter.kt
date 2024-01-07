package dev.baharudin.themoviedb.presentation.movie_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import dev.baharudin.themoviedb.databinding.ItemMovieCardBinding
import dev.baharudin.themoviedb.core.domain.entities.Genre
import dev.baharudin.themoviedb.core.domain.entities.Movie

class MovieListAdapter(
    private val context: Context,
    private val onClick: (Movie) -> Unit,
) : PagingDataAdapter<Movie, MovieListAdapter.ListViewHolder>(differCallback) {
    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ListViewHolder(private val binding: ItemMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?) {
            if (movie == null) return
            with(binding) {
                tvMovieTitle.text = movie.title
                tvMovieStoryline.text = movie.overview
                setThumbnail(movie.posterPath, ivMovieThumbnail)
                root.setOnClickListener { onClick(movie) }
            }
            setupGenreList(movie.genres)
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

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemMovieCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
}