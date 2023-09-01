package com.son.movie.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.son.movie.model.BookmarkRequest
import com.son.movie.model.Movie
import com.son.movie.network.ACCOUNT_ID
import com.son.movie.network.API_KEY
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MovieDetailsStatus { LOADING, ERROR, DONE }
enum class BookmarkStatus { LOADING, ERROR, DONE }
class DetailViewModel(val movieId: Int, application: Application) : AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

//    private val _movieTrailer = MutableLiveData<MovieTrailer?>()
//    val movieTrailer: LiveData<MovieTrailer?>
//        get() = _movieTrailer

    private val _showSnackbar = MutableLiveData<Boolean>(false)
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar
    init {
        getMovieDetails()
        checkBookmark()
    }

    private fun getMovieDetails() {
        coroutineScope.launch {
            Dispatchers.IO
            val movieDetailDeferred = MovieApi.retrofitService.getMovieDetailsAsync(movieId)
            _status.value = MovieDetailsStatus.LOADING
            try {
                _movie.value = movieDetailDeferred.await()
                _status.value = MovieDetailsStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = MovieDetailsStatus.ERROR
            }
        }
    }

    private fun checkBookmark() {
        coroutineScope.launch {
            try {
                val watchlistDeferred = MovieApi.retrofitService.getWatchlistMoviesAsync().await()
                _isBookmarked.value = watchlistDeferred.results.any { movie -> movie.id == movieId }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

     fun bookmark() {
        coroutineScope.launch {
            _bookmarkStatus.value = BookmarkStatus.LOADING
            try {
                val isBookmarked = _isBookmarked.value ?: false
                val bookmarkRequest = BookmarkRequest(movieId, "movie", !isBookmarked)

                val bookmarkDeferred = MovieApi.retrofitService
                    .addToWatchlistAsync(ACCOUNT_ID,bookmarkRequest, API_KEY).await()

                _isBookmarked.value = !isBookmarked
                _showSnackbar.value = true
                _bookmarkStatus.value = BookmarkStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _bookmarkStatus.value = BookmarkStatus.ERROR
            }
        }
    }

    fun setSnackbarVisibility(isShow: Boolean) {
        _showSnackbar.value = isShow
    }
//    private fun getTrailer() {
//        coroutineScope.launch {
//            Dispatchers.IO
//
//            try {
//                val movieTrailerDeferred = MovieApi.retrofitService.getTrailerMovieAsync(movieId)
//                val result = movieTrailerDeferred.await()
//                _movieTrailer.value = result.resultVideoMovie.find { it.type == "Trailer" }
//            } catch (t: Throwable) {
//                t.printStackTrace()
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}