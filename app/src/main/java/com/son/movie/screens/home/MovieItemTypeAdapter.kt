package com.son.movie.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.son.movie.databinding.ItemMovieTypeBinding
import com.son.movie.model.Movie

class MovieItemTypeAdapter :
    ListAdapter<Movie, MovieItemTypeAdapter.MovieItemTypeViewHolder>(MovieItemTypeDiffCallback) {

    inner class MovieItemTypeViewHolder(val binding: ItemMovieTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    companion object MovieItemTypeDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemTypeViewHolder {
        return MovieItemTypeViewHolder(ItemMovieTypeBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieItemTypeViewHolder, position: Int) {
        val movie = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickItem.onClick(movie)
//        }
        holder.bind(movie)
    }

    class OnclickItem(val onClickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = onClickListener(movie)
    }
}