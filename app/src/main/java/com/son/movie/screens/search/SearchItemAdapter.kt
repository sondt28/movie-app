package com.son.movie.screens.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.son.movie.databinding.ItemWatchlistBinding
import com.son.movie.model.Movie

class SearchItemAdapter(private val onClickItem: OnClickListener) :
    PagingDataAdapter<Movie, SearchItemAdapter.SearchItemViewHolder>(SearchDiffCallback) {

    inner class SearchItemViewHolder(private val binding: ItemWatchlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.movie = item
            binding.executePendingBindings()
        }
    }

    companion object SearchDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(ItemWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener {
            if (movie != null) {
                onClickItem.onClick(movie)
            }
        }
        if (movie != null) {
            holder.bind(movie)
        }
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}