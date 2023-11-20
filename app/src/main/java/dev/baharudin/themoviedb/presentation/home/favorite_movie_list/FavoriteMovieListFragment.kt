package dev.baharudin.themoviedb.presentation.home.favorite_movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.databinding.FragmentFavoriteMovieListBinding

/**
 * A fragment representing a list of favorite movies.
 */
@AndroidEntryPoint
class FavoriteMovieListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteMovieListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_movie_list, container, false)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteMovieListFragment()
    }
}