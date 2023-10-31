package dev.baharudin.themoviedb.presentation.movie_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.baharudin.themoviedb.databinding.ItemLoadMoreBinding

class LoadAdapter(
    private val onRetry: () -> Unit,
) : LoadStateAdapter<LoadAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLoadMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { onRetry() }
        }

        fun bind(state: LoadState) {
            binding.apply {
                progressBar.isVisible = state is LoadState.Loading
                tvRetry.isVisible = state is LoadState.Error
                btnRetry.isVisible = state is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            ItemLoadMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}