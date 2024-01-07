package dev.baharudin.themoviedb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.databinding.FragmentMovieDetailBinding
import dev.baharudin.themoviedb.presentation.common.toDate
import dev.baharudin.themoviedb.presentation.common.toImageUrl
import dev.baharudin.themoviedb.presentation.common.toString
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    companion object {
        const val EXTRA_MOVIE = "movie"
    }

    private lateinit var binding: FragmentMovieDetailBinding
    private val args: MovieDetailFragmentArgs by navArgs()

    private lateinit var movieDetailAdapter: MovieDetailAdapter

    @Inject
    lateinit var factory: MovieDetailViewModel.Factory
    private val movieDetailViewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModel.providesFactory(factory, args.movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        setupClickListener()
        setupHeader()
        setupViewPager()
    }

    private fun setupClickListener() {
        with(binding) {
            btnFavorite.setOnClickListener { movieDetailViewModel.toggleFavoriteButton() }
        }
    }

    private fun setupHeader() {
        val movie = args.movie

        binding.tvMovieTitle.text = movie.title

        val drawable = CircularProgressDrawable(requireContext())
        drawable.centerRadius = 30f
        drawable.strokeWidth = 10f
        drawable.start()
        Glide.with(this)
            .load(movie.posterPath.toImageUrl())
            .centerCrop()
            .placeholder(drawable)
            .into(binding.ivMovieThumbnail)

        movie.releaseDate?.toDate(format = "yyyy-MM-dd")?.let {
            binding.tvMovieYear.text = it.toString(format = "dd MMM yyyy")
        }


        binding.tvMovieRating.text = getString(R.string.rating, movie.voteAverage)

        movieDetailViewModel.movie.observe(viewLifecycleOwner) {
            it.data?.let { movie ->
                renderFavoriteButton(movie.isFavorite)
            }

            if (it.errorMessage.isNotBlank()) {
                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewPager() {
        movieDetailAdapter = MovieDetailAdapter(this, args.movie)

        with(binding) {
            vpRoot.adapter = movieDetailAdapter
            TabLayoutMediator(tlRoot, vpRoot) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.details)
                    }

                    1 -> {
                        tab.text = getString(R.string.reviews)
                    }
                }
            }.attach()
        }
    }

    private fun renderFavoriteButton(isFavorite: Boolean) {
        binding.btnFavorite.apply {
            when (isFavorite) {
                true -> {
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_bookmark_on, null)
                    backgroundTintList =
                        ContextCompat.getColorStateList(requireActivity(), R.color.colorPrimary)
                    iconTint =
                        ContextCompat.getColorStateList(requireActivity(), R.color.white)
                }

                false -> {
                    icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_bookmark_off, null)
                    iconTint =
                        ContextCompat.getColorStateList(requireActivity(), R.color.colorPrimary)
                    backgroundTintList =
                        ContextCompat.getColorStateList(requireActivity(), R.color.colorPrimary)
                    backgroundTintList =
                        ContextCompat.getColorStateList(requireActivity(), R.color.white)
                }
            }
        }
    }
}