package com.son.movie.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.son.movie.model.BookmarkRequest
import com.son.movie.model.Movie
import com.son.movie.model.Trailer
import com.son.movie.model.Trailers
import com.son.movie.network.MovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class MovieDetailsStatus { LOADING, ERROR, DONE }
enum class BookmarkStatus { LOADING, ERROR, DONE }
class DetailViewModel(val movieId: Int, application: Application) : AndroidViewModel(application) {
    private val _status = MutableLiveData<MovieDetailsStatus>()
    val status: LiveData<MovieDetailsStatus>
        get() = _status

    private val _bookmarkStatus = MutableLiveData<BookmarkStatus>()
    val bookmarkStatus: LiveData<BookmarkStatus>
        get() = _bookmarkStatus

    private val _isBookmarked = MutableLiveData<Boolean>(false)
    val isBookmarked: LiveData<Boolean>
        get() = _isBookmarked

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _navigateToYoutube = MutableLiveData<String?>()
    val navigateToYoutube: LiveData<String?>
        get() = _navigateToYoutube

    private val _showSnackbar = MutableLiveData<Boolean>(false)
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    init {
        onMovieDetails()
        onBookmark()
    }

    private fun onMovieDetails() {
        viewModelScope.launch {
            _status.value = MovieDetailsStatus.LOADING
            try {
                val movie = fetchMovieDetails(movieId)
                _movie.value = movie
                _status.value = MovieDetailsStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = MovieDetailsStatus.ERROR
            }
        }
    }

    private suspend fun fetchMovieDetails(movieId: Int): Movie {
        return withContext(Dispatchers.IO) {
            val movieDetailDeferred = MovieApi.retrofitService.getMovieDetailsAsync(movieId)
            movieDetailDeferred.await()
        }
    }

    private fun onBookmark() {
        viewModelScope.launch {
            try {
                _isBookmarked.value = checkBookmark()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    private suspend fun checkBookmark(): Boolean {
        return withContext(Dispatchers.IO) {
            val isBookmark = MovieApi.retrofitService.getWatchlistMoviesAsync().await()
            isBookmark.results.any { movie -> movie.id == movieId }
        }
    }

    fun bookmark() {
        viewModelScope.launch {
            _bookmarkStatus.value = BookmarkStatus.LOADING
            try {
                val isBookmarked = _isBookmarked.value ?: false
                val bookmarkRequest = BookmarkRequest(mediaId = movieId, watchlist = !isBookmarked)
                MovieApi.retrofitService.addToWatchlistAsync(bookmarkRequest = bookmarkRequest).await()

                _isBookmarked.value = !isBookmarked
                _showSnackbar.value = true
                _bookmarkStatus.value = BookmarkStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _bookmarkStatus.value = BookmarkStatus.ERROR
            }
        }
    }

    fun onTrailer() {
        viewModelScope.launch {
            try {
                val movieTrailer = getTrailer(movieId)
                _navigateToYoutube.value = movieTrailer?.key
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    private suspend fun getTrailer(movieId: Int): Trailer? {
        return withContext(Dispatchers.IO) {
            val result = MovieApi.retrofitService.getTrailerMovieAsync(movieId).await()
            result.resultVideoMovie.find { it.type == "Trailer" }
        }
    }

    fun setSnackbarVisibility(isShow: Boolean) {
        _showSnackbar.value = isShow
    }

    fun navigateToYoutubeCompleted() {
        _navigateToYoutube.value = null
    }
}