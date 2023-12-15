package dev.baharudin.themoviedb.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.databinding.FragmentHomeBinding
import dev.baharudin.themoviedb.presentation.home.genre_list.GenreListFragment

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val fragmentManager: FragmentManager = childFragmentManager
        val genreFragment = GenreListFragment.newInstance()
        val genreTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        genreTransaction.replace(binding.containerGenres.id, genreFragment)
        genreTransaction.commit()

        // TODO: Add popular movies
        // val favoriteMovieFragment = FavoriteMovieListFragment.newInstance()
        // val favoriteMovieTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        // favoriteMovieTransaction.replace(binding.containerFavoriteMovies.id, favoriteMovieFragment)
        // favoriteMovieTransaction.commit()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupAppBar()
        setupFab()
    }

    private fun setupAppBar() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btn_about -> {
                    val toAboutFragment =
                        HomeFragmentDirections.actionHomeFragmentToAboutFragment()
                    view?.findNavController()?.navigate(toAboutFragment)
                }
            }
            true
        }
    }

    private fun setupFab() {
        binding.fabFavorite.setOnClickListener {
            try {
                startActivity(
                    Intent(
                        requireContext(),
                        Class.forName("dev.baharudin.themoviedb.favorite.presentation.FavoriteActivity")
                    )
                )
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Module not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}