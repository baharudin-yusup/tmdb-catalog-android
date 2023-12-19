package dev.baharudin.themoviedb.presentation.home.genre_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.baharudin.themoviedb.databinding.ItemGenreCardBinding
import dev.baharudin.themoviedb.core.domain.entities.Genre

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