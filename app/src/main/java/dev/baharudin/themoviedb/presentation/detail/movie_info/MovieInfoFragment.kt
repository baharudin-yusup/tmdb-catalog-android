package dev.baharudin.themoviedb.presentation.detail.movie_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dev.baharudin.themoviedb.databinding.FragmentMovieInfoBinding
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.presentation.detail.movie_review.MovieReviewFragment
import dev.baharudin.themoviedb.presentation.detail.movie_review.MovieReviewFragmentArgs

class MovieInfoFragment : Fragment() {

    companion object {
        fun newInstance(movie: Movie): MovieInfoFragment {
            val apply = MovieInfoFragment().apply {
                arguments = Bundle(1).apply {
                    putParcelable("movie", movie)
                }
            }
            return apply
        }
    }

    private val args: MovieInfoFragmentArgs by navArgs()


    private lateinit var binding: FragmentMovieInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        with(binding) {
            tvMovieStoryline.text = args.movie.overview
        }
    }
}