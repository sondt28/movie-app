package com.son.movie.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.son.movie.model.BookmarkRequest
import com.son.movie.model.Movie
import com.son.movie.model.Trailer
import com.son.movie.network.MovieApi
import com.son.movie.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class MovieDetailsStatus { LOADING, ERROR, DONE }
enum class BookmarkStatus { LOADING, ERROR, DONE }

class DetailViewModel @AssistedInject constructor(
    private val repository: MovieRepository,
    @Assisted val movieId: Int,
    application: Application
) : AndroidViewModel(application) {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(movieId: Int): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            movieId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(movieId) as T
            }
        }
    }

    private val _status = MutableLiveData<MovieDetailsStatus>()
    val status: LiveData<MovieDetailsStatus> = _status

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _bookmarkStatus = MutableLiveData<BookmarkStatus>()
    val bookmarkStatus: LiveData<BookmarkStatus> = _bookmarkStatus

    private val _isBookmarked = MutableLiveData(false)
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    private val _navigateToYoutube = MutableLiveData<String?>()
    val navigateToYoutube: LiveData<String?> = _navigateToYoutube

    private val _navigateToPreviousDestination = MutableLiveData<Boolean>()
    val navigateToPreviousDestination: LiveData<Boolean> = _navigateToPreviousDestination

    private val _showSnackbar = MutableLiveData(false)
    val showSnackbar: LiveData<Boolean> = _showSnackbar

    init {
        onMovieDetails()
        onBookmark()
    }

    private fun onMovieDetails() {
        viewModelScope.launch {
            _status.value = MovieDetailsStatus.LOADING
            try {
                _movie.value = repository.getMovieDetailsAsync(movieId)
                _status.value = MovieDetailsStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = MovieDetailsStatus.ERROR
            }
        }
    }

    private fun onBookmark() {
        viewModelScope.launch {
            try {
                val watchlistMovies = repository.getWatchlistMoviesAsync()
                _isBookmarked.value = watchlistMovies.results.any { movie -> movie.id == movieId }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun bookmarkMovie() {
        viewModelScope.launch {
            _bookmarkStatus.value = BookmarkStatus.LOADING
            try {
                val isBookmarked = _isBookmarked.value ?: false
                val bookmarkRequest = BookmarkRequest(mediaId = movieId, watchlist = !isBookmarked)
                repository.addToWatchlistAsync(bookmarkRequest = bookmarkRequest)

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
                val movieVideos = repository.getMovieVideosAsync(movieId)
                val trailer = movieVideos.resultVideoMovie.find { it.type == "Trailer" }
                _navigateToYoutube.value = trailer?.key
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun setSnackbarVisibility(isShow: Boolean) {
        _showSnackbar.value = isShow
    }

    fun navigateToYoutubeCompleted() {
        _navigateToYoutube.value = null
    }

    fun backToPreviousDes() {
        _navigateToPreviousDestination.value = true
    }

    fun backToPreviousDesCompleted() {
        _navigateToPreviousDestination.value = false
    }
}