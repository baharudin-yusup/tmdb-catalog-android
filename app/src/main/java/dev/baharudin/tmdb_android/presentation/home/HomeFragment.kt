package dev.baharudin.tmdb_android.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.tmdb_android.R
import dev.baharudin.tmdb_android.core.domain.entities.Genre
import dev.baharudin.tmdb_android.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupAppBar()
        setupFab()
        setupGenreList()
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

    // Setup Genre
    private fun setupGenreList() {
        binding.btnRetry.setOnClickListener { homeViewModel.fetchMovieGenre() }

        homeViewModel.genreList.observe(viewLifecycleOwner) {
            if (it.data != null) {
                showGenreList(it.data)
            }

            updateProgressBar(it.isLoading)

            val errorMessage = it.errorMessage
            if (errorMessage.isNotBlank()) {
                binding.llRetry.isVisible = true
                Toast.makeText(requireActivity(), it.errorMessage, Toast.LENGTH_SHORT).show()
            } else {
                binding.llRetry.isVisible = false
            }
        }

    }

    private fun updateProgressBar(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showGenreList(genres: List<Genre>) {
        val genreListAdapter = GenreListAdapter(genres) {
            val toMovieListFragment =
                HomeFragmentDirections.actionHomeFragmentToMovieListFragment(it)
            view?.findNavController()
                ?.navigate(toMovieListFragment)
        }
        binding.rvGenres.setHasFixedSize(true)
        binding.rvGenres.layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
        }
        binding.rvGenres.adapter = genreListAdapter
    }
}