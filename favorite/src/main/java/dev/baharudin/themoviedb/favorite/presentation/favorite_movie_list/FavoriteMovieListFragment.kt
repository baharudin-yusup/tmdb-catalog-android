package dev.baharudin.themoviedb.favorite.presentation.favorite_movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.EntryPointAccessors
import dev.baharudin.themoviedb.di.FavoriteModuleDependencies
import dev.baharudin.themoviedb.favorite.databinding.FragmentFavoriteMovieListBinding
import dev.baharudin.themoviedb.favorite.di.DaggerFavoriteComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteMovieListFragment : Fragment() {

    @Inject
    lateinit var favoriteMovieListViewModel: FavoriteMovieListViewModel

    private lateinit var binding: FragmentFavoriteMovieListBinding
    private lateinit var favoriteMovieListAdapter: FavoriteMovieListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerFavoriteComponent.factory().create(
            EntryPointAccessors.fromApplication(requireContext(), FavoriteModuleDependencies::class.java)
        ).inject(this)

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

                        else -> {
                            dataState.data?.let { movies ->
                                favoriteMovieListAdapter =
                                    FavoriteMovieListAdapter(requireContext(), movies) {
                                        val toDetailActivity =
                                            FavoriteMovieListFragmentDirections.actionFavoriteMovieListFragmentToMovieDetailFragment(
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
}