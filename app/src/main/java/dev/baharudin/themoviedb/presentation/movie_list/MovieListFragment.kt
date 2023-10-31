package dev.baharudin.themoviedb.presentation.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.databinding.FragmentMovieListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val args: MovieListFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieListBinding

    private lateinit var adapter: MovieListAdapter

    @Inject
    lateinit var factory: MovieListViewModel.Factory
    private val movieListViewModel: MovieListViewModel by viewModels {
        MovieListViewModel.providesFactory(factory, args.genre)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        adapter = MovieListAdapter(requireContext()) {
            val toDetailActivity =
                MovieListFragmentDirections.actionMovieListFragmentToDetailFragment(it)
            view?.findNavController()
                ?.navigate(toDetailActivity)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        collectState()
    }

    private fun initView() {
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.adapter =
            adapter.withLoadStateFooter(LoadAdapter { adapter.retry() })

        binding.toolbar.title = getString(R.string.movie_list_fragment_title, args.genre.name)
        binding.btnRetry.setOnClickListener { adapter.retry() }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            movieListViewModel.movies.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    val state = it.refresh
                    with(binding) {
                        llRetry.isVisible = state is LoadState.Error
                        progressBar.isVisible = state is LoadState.Loading
                    }

                    if (state is LoadState.Error) {
                        Toast.makeText(requireActivity(), state.error.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}