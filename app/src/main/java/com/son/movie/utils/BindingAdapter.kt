package com.son.movie.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.son.movie.R
import com.son.movie.model.Movie
import com.son.movie.screens.home.MovieItemAdapter

const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

@BindingAdapter("videoPath")
fun bindVideoId(youtubePlayerView: YouTubePlayerView, videoId: String?) {
    videoId?.let {
        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0F)
            }
        })
    }
}
@BindingAdapter("runtimeFormat")
fun bindRuntimeFormat(textView: TextView, runtime: Int?) {
    runtime?.let {
        textView.text = convertRuntimeToFormatted(runtime, textView.context.resources)
    }
}

@BindingAdapter("listData")
fun bindRecycleView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieItemAdapter
    adapter.submitList(data)
}

@BindingAdapter("imgUrl")
fun bindImage(imgView: ImageView, posterPath: String?) {
    posterPath?.let {
        val imgUrl = IMAGE_URL + posterPath
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context).load(imgUri).apply(
            RequestOptions().placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)

        ).into(imgView)
    }
}

@BindingAdapter("movieStatus")
fun bindStatus(statusImageView: ImageView, status: MovieStatus?) {
    when (status) {
        MovieStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        MovieStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }

        MovieStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }

        else -> {

        }
    }
}