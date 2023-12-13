package dev.baharudin.themoviedb.presentation.home.genre_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.baharudin.themoviedb.databinding.FragmentGenreListBinding
import dev.baharudin.themoviedb.domain.entities.Genre
import dev.baharudin.themoviedb.presentation.home.HomeFragmentDirections

@AndroidEntryPoint
class GenreListFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = GenreListFragment()
    }

    private val genreListViewModel: GenreListViewModel by activityViewModels()
    private lateinit var binding: FragmentGenreListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupGenreList()

        binding.btnRetry.setOnClickListener { genreListViewModel.fetchMovieGenre() }
    }

    private fun setupGenreList() {

        genreListViewModel.genreList.observe(viewLifecycleOwner) {
            if (it.data != null) {
                showGenreList(it.data)
            }

            updateProgressBar(it.isLoading)

            val errorMessage = it.errorMessage
            if (errorMessage.isNotBlank()) {
                binding.llRetry.isVisible = true
                Toast.makeText(requireActivity(), it.errorMessage, Toast.LENGTH_SHORT).show()
            } else {
                binding.llRetry.isVisible = false
            }
        }

    }

    private fun updateProgressBar(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showGenreList(genres: List<Genre>) {
        val genreListAdapter = GenreListAdapter(genres) {
            val toMovieListFragment =
                HomeFragmentDirections.actionHomeFragmentToMovieListFragment(it)
            view?.findNavController()
                ?.navigate(toMovieListFragment)
        }
        binding.rvGenres.setHasFixedSize(true)
        binding.rvGenres.layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
        }
        binding.rvGenres.adapter = genreListAdapter
    }
}