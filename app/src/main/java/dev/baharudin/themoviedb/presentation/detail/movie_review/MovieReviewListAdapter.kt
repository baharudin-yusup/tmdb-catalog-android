package dev.baharudin.themoviedb.presentation.detail.movie_review

import android.R
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import dev.baharudin.themoviedb.databinding.ItemReviewCardBinding
import dev.baharudin.themoviedb.domain.entities.Review
import dev.baharudin.themoviedb.presentation.common.toDate
import dev.baharudin.themoviedb.presentation.common.toImageUrl
import dev.baharudin.themoviedb.presentation.common.toString


class MovieReviewListAdapter(private val context: Context
) : PagingDataAdapter<Review, MovieReviewListAdapter.ListViewHolder>(differCallback) {
    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ListViewHolder(private val binding: ItemReviewCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review?) {
            if (review == null) return
            with(binding) {
                review.author.avatarPath?.let { setupAvatar(it.toImageUrl()) }
                tvName.text = review.author.name.ifBlank { review.author.username }
                setupReviewContent(review.content)
                val date = review.updatedAt.toDate()
                date?.let {
                    tvDate.text = it.toString(format = "EEE, dd MMM yyyy")
                }
            }
        }


        private fun setupAvatar(url: String) {
            val drawable = CircularProgressDrawable(context)
            drawable.centerRadius = 10f;
            drawable.strokeWidth = 3f;
            drawable.start()

            Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(drawable)
                .into(binding.ivAvatar)
        }

        private fun setupReviewContent(content: String) {
            binding.tvContent.text = content

            // TODO: Add read more button
        }

        private fun hasEllipsis(): Boolean {
            var hasLongContent = false
            val descriptionLayout = binding.tvContent.layout
            if (descriptionLayout != null) {
                val lines = descriptionLayout.lineCount
                if (lines > 0) {
                    hasLongContent = descriptionLayout.getEllipsisCount(lines - 1) > 0
                }
            }
            return hasLongContent
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemReviewCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }
}