package com.son.movie.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makeramen.roundedimageview.RoundedImageView
import com.son.movie.R
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.screens.home.MovieItemAdapter
import com.son.movie.screens.home.MovieItemTypeAdapter
import com.son.movie.screens.search.SearchItemAdapter

const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

@BindingAdapter("listTypeMovies")
fun bindNowPlayingRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieItemTypeAdapter
    adapter.submitList(data)
}

@BindingAdapter("listSearch")
fun bindSearchRecyclerView(recyclerView: RecyclerView, data: Movies?) {
    val adapter = recyclerView.adapter as SearchItemAdapter
    adapter.submitList(data?.results)
}

//@BindingAdapter("checkResult")
//fun checkSearchResult(textView: TextView, searchResult: Movies?) {
//    if (searchResult?.results.isNullOrEmpty()) {
//        textView.text = R.string.no_results_found.toString()
//    } else {
//        textView.text = ""
//    }
//}

@BindingAdapter("runtimeFormat")
fun bindRuntimeFormat(textView: TextView, runtime: Int?) {
    runtime?.let {
        textView.text = convertRuntimeToFormatted(runtime, textView.context.resources)
    }
}
@BindingAdapter("releaseFormat")
fun bindReleaseFormat(textView: TextView, release: String?) {
    release?.let {
        textView.text = convertReleaseToFormatted(release)
    }
}
@BindingAdapter("voteScoreFormat")
fun bindVoteScoreFormat(textView: TextView, voteScore: Float?) {
    voteScore?.let {
        textView.text = convertVoteScoreFormatted(voteScore)
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

@BindingAdapter("shimmer")
fun bindingStatus(shimmerFrameLayout: ShimmerFrameLayout, status: MovieStatus?) {
    when (status) {
        MovieStatus.ERROR -> {

        }
        MovieStatus.DONE -> {
            shimmerFrameLayout.visibility = View.GONE
        }
        MovieStatus.LOADING -> {
            shimmerFrameLayout.visibility = View.VISIBLE
        }
        else -> {
            shimmerFrameLayout.visibility = View.VISIBLE
        }
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
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        else -> {

        }
    }
}