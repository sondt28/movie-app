package com.son.movie.screens.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.son.movie.databinding.ItemWatchlistBinding
import com.son.movie.model.Movie

class WatchlistItemAdapter(private val onClickItem: OnclickItem) :
    ListAdapter<Movie, WatchlistItemAdapter.WatchlistItemViewHolder>(WatchlistDiffCallBack) {

    inner class WatchlistItemViewHolder(private val binding: ItemWatchlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistItemViewHolder {
        return WatchlistItemViewHolder(
            ItemWatchlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WatchlistItemViewHolder, position: Int) {
        val movie = getItem(position)
        holder.itemView.setOnClickListener { onClickItem.onClickMovie(movie) }
        holder.bind(movie)
    }

    class OnclickItem(private val onClickItem: (movie: Movie) -> Unit) {
        fun onClickMovie(movie: Movie) = onClickItem(movie)
    }

    companion object WatchlistDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}