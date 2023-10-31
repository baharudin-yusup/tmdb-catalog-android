package dev.baharudin.themoviedb.presentation.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.databinding.FragmentDetailBinding

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = args.movie
        binding.tvMovieTitle.text = movie.title

        val drawable = CircularProgressDrawable(requireContext())
        drawable.centerRadius = 30f;
        drawable.strokeWidth = 10f;
        drawable.start()
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
            .centerCrop()
            .placeholder(drawable)
            .into(binding.imgMovieThumbnail)

//        binding.tvMovieStoryline.text = movie.overview
        binding.tvMovieYear.text = movie.releaseDate
        binding.tvMovieRating.text = getString(R.string.rating, movie.voteAverage)
    }
}