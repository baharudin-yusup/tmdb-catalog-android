package dev.baharudin.tmdb_android.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.baharudin.tmdb_android.databinding.ItemGenreCardBinding
import dev.baharudin.tmdb_android.core.domain.entities.Genre

class


GenreListAdapter(
    private val genres: List<Genre>,
    private val onClick: (Genre) -> Unit,
) :
    RecyclerView.Adapter<GenreListAdapter.ListViewHolder>() {
    inner class ListViewHolder(var binding: ItemGenreCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemGenreCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val genre = genres[position]

        holder.binding.tvGenreName.text = genre.name
        holder.itemView.setOnClickListener { onClick(genre) }
    }
}