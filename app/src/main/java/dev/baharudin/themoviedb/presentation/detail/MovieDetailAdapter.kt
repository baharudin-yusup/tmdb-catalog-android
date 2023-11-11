package dev.baharudin.themoviedb.presentation.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.baharudin.themoviedb.domain.entities.Movie
import dev.baharudin.themoviedb.presentation.detail.movie_info.MovieInfoFragment
import dev.baharudin.themoviedb.presentation.detail.movie_review.MovieReviewFragment

class MovieDetailAdapter(fragment: MovieDetailFragment, private val movie: Movie): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                MovieInfoFragment.newInstance(movie)
            }

            else -> {
                MovieReviewFragment.newInstance(movie)
            }
        }
    }
}