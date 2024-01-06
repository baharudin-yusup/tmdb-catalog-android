package dev.baharudin.tmdb_android.presentation.detail
//
//import androidx.fragment.app.Fragment
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import dev.baharudin.tmdb_android.core.domain.entities.Movie
//import dev.baharudin.tmdb_android.presentation.detail.movie_info.MovieInfoFragment
//import dev.baharudin.tmdb_android.presentation.detail.movie_review.MovieReviewFragment
//
//class MovieDetailAdapter(fragment: MovieDetailFragment, private val movie: Movie): FragmentStateAdapter(fragment) {
//    override fun getItemCount(): Int = 2
//
//    override fun createFragment(position: Int): Fragment {
//        return when(position) {
//            0 -> {
//                MovieInfoFragment.newInstance(movie)
//            }
//
//            else -> {
//                MovieReviewFragment.newInstance(movie)
//            }
//        }
//    }
//}