package com.son.movie.screens.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class WatchListStatus { LOADING, ERROR, DONE }

@HiltViewModel
class WatchlistViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _status = MutableLiveData<WatchListStatus>()
    val status: LiveData<WatchListStatus> = _status

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies> = _movies

    private val _navigateToMovieDetail = MutableLiveData<Int?>()
    val navigateToMovieDetail: LiveData<Int?> = _navigateToMovieDetail

    init {
        getOwnWatchlist()
    }

     fun getOwnWatchlist() {
        viewModelScope.launch {
            _status.value = WatchListStatus.LOADING
            try {
                _movies.value = repository.getWatchlistMoviesAsync()
                _status.value = WatchListStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = WatchListStatus.ERROR
            }
        }
    }

    fun navigateToMovieDetail(movieId: Int) {
        _navigateToMovieDetail.value = movieId
    }

    fun doneNavigateToMovieDetail() {
        _navigateToMovieDetail.value = null
    }
}