package dev.baharudin.themoviedb.presentation.detail.movie_review

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
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.databinding.FragmentMovieReviewBinding
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.presentation.common.LoadAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieReviewFragment : Fragment() {

    companion object {
        fun newInstance(movie: Movie): MovieReviewFragment {
            val apply = MovieReviewFragment().apply {
                arguments = Bundle(1).apply {
                    putParcelable("movie", movie)
                }
            }
            return apply
        }
    }

    private val args: MovieReviewFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieReviewBinding

    private lateinit var movieReviewListAdapter: MovieReviewListAdapter

    @Inject
    lateinit var factory: MovieReviewViewModel.Factory
    private val movieReviewViewModel: MovieReviewViewModel by viewModels {
        MovieReviewViewModel.providesFactory(factory, args.movie)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieReviewBinding.inflate(inflater, container, false)
        movieReviewListAdapter = MovieReviewListAdapter(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        collectState()
    }

    private fun setupUi() {
        setupReviewList()
    }

    private fun setupReviewList() {
        binding.rvReviews.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                movieReviewListAdapter.withLoadStateFooter(LoadAdapter { movieReviewListAdapter.retry() })
        }

    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            movieReviewViewModel.reviews.collectLatest {
                movieReviewListAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieReviewListAdapter.loadStateFlow.collect {
                    val state = it.refresh
                    with(binding) {
                        llRetry.isVisible = state is LoadState.Error
                        pbRoot.isVisible = state is LoadState.Loading

                        when (state) {
                            is LoadState.Error -> {
                                Toast.makeText(
                                    requireActivity(),
                                    state.error.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                tvNoReviews.isVisible = false
                            }

                            is LoadState.Loading -> {
                                tvNoReviews.isVisible = false
                            }

                            else -> {
                                binding.tvNoReviews.isVisible =
                                    movieReviewListAdapter.itemCount == 0
                            }
                        }
                    }
                }
            }
        }
    }

}