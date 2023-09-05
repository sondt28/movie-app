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
import com.son.movie.R
import com.son.movie.model.Genre
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.screens.detail.BookmarkStatus
import com.son.movie.screens.home.MovieItemAdapter
import com.son.movie.screens.home.MovieItemTypeAdapter
import com.son.movie.screens.search.SearchItemAdapter
import com.son.movie.screens.watchlist.WatchlistItemAdapter

const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

@BindingAdapter("genresName")
fun bindGenresName(textView: TextView, genres: List<Genre>?) {
    if (genres?.isNotEmpty() == true) {
        textView.text = genres[0].name
    } else {
        textView.text = textView.context.getString(R.string.no_genre)
    }
}

@BindingAdapter("listWatchlist")
fun bindWatchlistRecyclerView(recyclerView: RecyclerView, data: Movies?) {
    val adapter = recyclerView.adapter as WatchlistItemAdapter
    adapter.submitList(data?.results)
}

@BindingAdapter("checkBookmark")
fun bindCheckBookmark(imgView: ImageView, isBookmarked: Boolean) {
    if (isBookmarked) imgView.setImageResource(R.drawable.ic_book_mark_selected)
    else imgView.setImageResource(R.drawable.ic_book_mark)
}

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

@BindingAdapter("checkBookmarkStatus")
fun checkSearchResult(imgView: ImageView, bookmarkStatus: BookmarkStatus?) {
    when (bookmarkStatus) {
        BookmarkStatus.LOADING -> {
            imgView.visibility = View.VISIBLE
            imgView.setImageResource(R.drawable.loading_animation)
        }

        BookmarkStatus.DONE -> {
            imgView.visibility = View.GONE
        }

        BookmarkStatus.ERROR -> {
            imgView.visibility = View.GONE
        }

        else -> {
            imgView.visibility = View.GONE
        }
    }
}

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