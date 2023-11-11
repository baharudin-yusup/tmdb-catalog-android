package dev.baharudin.themoviedb.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.databinding.FragmentMovieDetailBinding
import dev.baharudin.themoviedb.presentation.common.toImageUrl

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args: MovieDetailFragmentArgs by navArgs()

    private lateinit var movieDetailAdapter: MovieDetailAdapter

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
        setupHeader()
        setupViewPager()
    }

    private fun setupHeader() {
        val movie = args.movie
        binding.tvMovieTitle.text = movie.title

        val drawable = CircularProgressDrawable(requireContext())
        drawable.centerRadius = 30f;
        drawable.strokeWidth = 10f;
        drawable.start()
        Glide.with(this)
            .load(movie.posterPath.toImageUrl())
            .centerCrop()
            .placeholder(drawable)
            .into(binding.ivMovieThumbnail)

        binding.tvMovieYear.text = movie.releaseDate
        binding.tvMovieRating.text = getString(R.string.rating, movie.voteAverage)
    }

    private fun setupViewPager() {
        movieDetailAdapter = MovieDetailAdapter(this, args.movie)

        with(binding) {
            vpRoot.adapter = movieDetailAdapter
            TabLayoutMediator(tlRoot, vpRoot) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.info)
                    }

                    1 -> {
                        tab.text = getString(R.string.reviews)
                    }
                }
            }.attach()
        }
    }
}