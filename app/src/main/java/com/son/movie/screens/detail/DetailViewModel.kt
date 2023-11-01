package com.son.movie.screens.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.son.movie.model.BookmarkRequest
import com.son.movie.model.Movie
import com.son.movie.repository.MovieRepository
import com.son.movie.utils.DataResult
import com.son.movie.utils.Event
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

enum class BookmarkStatus { LOADING, ERROR, DONE }

class DetailViewModel @AssistedInject constructor(
    private val repository: MovieRepository,
    @Assisted val movieId: Int
) : ViewModel() {

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

    private val _movie = MutableLiveData<DataResult<Movie>>()
    val movie: LiveData<DataResult<Movie>> = _movie

    private val _bookmarkStatus = MutableLiveData<BookmarkStatus>()
    val bookmarkStatus: LiveData<BookmarkStatus> = _bookmarkStatus

    private val _isBookmarked = MutableLiveData(false)
    val isBookmarked: LiveData<Boolean> = _isBookmarked

    private val _navigateToYoutube = MutableLiveData<Event<String?>>()
    val navigateToYoutube: LiveData<Event<String?>> = _navigateToYoutube

    private val _navigateToPreviousDestination = MutableLiveData<Event<Boolean>>()
    val navigateToPreviousDestination: LiveData<Event<Boolean>> = _navigateToPreviousDestination

    private val _showSnackbar = MutableLiveData<Event<Boolean>>()
    val showSnackbar: LiveData<Event<Boolean>> = _showSnackbar

    init {
        getMovieDetails()
        checkBookmarked()
    }

    private fun getMovieDetails() = viewModelScope.launch {
        DataResult.loading(null)
        try {
            _movie.value = DataResult.success(repository.getMovieDetailsAsync(movieId))
        } catch (t: Throwable) {
            t.printStackTrace()
            DataResult.error(t.message, null)
        }
    }

    private fun checkBookmarked() = viewModelScope.launch {
        try {
            val watchlistMovies = repository.getWatchlistMoviesAsync()
            _isBookmarked.value = watchlistMovies.results.any { movie -> movie.id == movieId }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun bookmark() = viewModelScope.launch {
        DataResult.loading(null)
        try {
            val isBookmarked = _isBookmarked.value ?: false
            val bookmarkRequest = BookmarkRequest(mediaId = movieId, watchlist = !isBookmarked)

            DataResult.success(repository.addToWatchlistAsync(bookmarkRequest = bookmarkRequest))

            _isBookmarked.value = !isBookmarked
            _showSnackbar.value = Event(true)
        } catch (t: Throwable) {
            t.printStackTrace()
            _bookmarkStatus.value = BookmarkStatus.ERROR
        }
    }


    fun getTrailer() = viewModelScope.launch {
        try {
            val movieVideos = repository.getMovieVideosAsync(movieId)
            val trailer = movieVideos.resultVideoMovie.find { it.type == "Trailer" }

            _navigateToYoutube.value = Event(trailer?.key)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun backToPreviousDes() {
        _navigateToPreviousDestination.value = Event(true)
    }
}