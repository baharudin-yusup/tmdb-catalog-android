package dev.baharudin.themoviedb.presentation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.R
import dev.baharudin.themoviedb.core.domain.entities.Movie
import dev.baharudin.themoviedb.presentation.detail.MovieDetailFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FRAGMENT_DESTINATION = "fragment_destination"
        const val EXTRA_PARCELABLE = "parcelable"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
    }

    private fun initFragment() {
        // TODO: Improve this
        val destination = intent.getStringExtra(EXTRA_FRAGMENT_DESTINATION) ?: return
        when (destination) {
            MovieDetailFragment::class.java.simpleName -> {
                val movie = if (Build.VERSION.SDK_INT >= 33) {
                    intent.getParcelableExtra(
                        EXTRA_PARCELABLE,
                        Movie::class.java
                    ) ?: return
                } else {
                    intent.getParcelableExtra(
                        EXTRA_PARCELABLE
                    ) ?: return
                }

                val transaction = supportFragmentManager.beginTransaction()

                val fragment = MovieDetailFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable(MovieDetailFragment.EXTRA_MOVIE, movie)
                }

                transaction.replace(R.id.root_container, fragment)
                transaction.commit()
            }
        }
    }
}