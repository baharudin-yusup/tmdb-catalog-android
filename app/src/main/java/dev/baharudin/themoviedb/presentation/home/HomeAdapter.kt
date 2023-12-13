package dev.baharudin.themoviedb.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import dev.baharudin.themoviedb.databinding.ItemFragmentBinding

class HomeAdapter(
    private val genreListFragment: Fragment,
    private val favoriteMovieListFragment: Fragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_GENRE = 0
        private const val VIEW_TYPE_FAVORITE_MOVIE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_GENRE -> {
                val binding = ItemFragmentBinding.inflate(inflater, parent, false)
                GenreViewHolder(binding)
            }

            VIEW_TYPE_FAVORITE_MOVIE -> {
                val binding = ItemFragmentBinding.inflate(inflater, parent, false)
                FavoriteMovieViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_GENRE -> {
                val genreHolder = holder as GenreViewHolder
                genreHolder.bind(genreListFragment)
            }

            VIEW_TYPE_FAVORITE_MOVIE -> {
                val favoriteMovieHolder = holder as FavoriteMovieViewHolder
                favoriteMovieHolder.bind(favoriteMovieListFragment)
            }
        }
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_GENRE
            1 -> VIEW_TYPE_FAVORITE_MOVIE
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    inner class GenreViewHolder(private val binding: ItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fragment: Fragment) {
            val fragmentManager = (binding.root.context as FragmentActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(binding.rootContainer.id, fragment)
                .commit()
        }
    }

    inner class FavoriteMovieViewHolder(private val binding: ItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fragment: Fragment) {
            val fragmentManager = (binding.root.context as FragmentActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(binding.rootContainer.id, fragment)
                .commit()
        }
    }
}
