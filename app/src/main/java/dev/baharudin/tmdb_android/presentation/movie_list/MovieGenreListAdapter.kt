package dev.baharudin.tmdb_android.presentation.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.baharudin.tmdb_android.databinding.ItemMovieGenreCardBinding
import dev.baharudin.tmdb_android.core.domain.entities.Genre

class MovieGenreListAdapter(private val genres: List<Genre>): RecyclerView.Adapter<MovieGenreListAdapter.ListViewHolder>() {
    class ListViewHolder(private var binding: ItemMovieGenreCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.tvGenreName.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemMovieGenreCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val genre = genres[position]
        holder.bind(genre)
    }
}