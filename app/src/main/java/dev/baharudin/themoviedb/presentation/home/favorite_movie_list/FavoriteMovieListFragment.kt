package dev.baharudin.themoviedb.presentation.home.favorite_movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.databinding.FragmentFavoriteMovieListBinding
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of favorite movies.
 */
@AndroidEntryPoint
class FavoriteMovieListFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = FavoriteMovieListFragment()
    }

    private lateinit var binding: FragmentFavoriteMovieListBinding
    private val favoriteMovieListViewModel: FavoriteMovieListViewModel by viewModels()
    private lateinit var favoriteMovieListAdapter: FavoriteMovieListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteMovieListViewModel.favoriteMovieStateFlow.collect { dataState ->
                    when {
                        dataState.isLoading -> {
                            // TODO: Show loading state
                        }

                        dataState.errorMessage.isNotBlank() -> {
                            // TODO: Show error message
                        }

                        dataState.data != null -> {
                            val movies = dataState.data
                            favoriteMovieListAdapter =
                                FavoriteMovieListAdapter(requireContext(), movies) {
                                    val toDetailActivity =
                                        FavoriteMovieListFragmentDirections.actionFavoriteMovieListFragmentToDetailFragment(
                                            it
                                        )
                                    view?.findNavController()
                                        ?.navigate(toDetailActivity)
                                }

                            binding.rvFavoriteMovies.setHasFixedSize(true)
                            binding.rvFavoriteMovies.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.rvFavoriteMovies.adapter = favoriteMovieListAdapter
                        }
                    }
                }
            }
        }
    }
}