package com.son.movie.screens.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import com.son.movie.utils.DataResult
import com.son.movie.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _movies = MutableLiveData<DataResult<Movies>>()
    val movies: LiveData<DataResult<Movies>> = _movies

    private val _navigateToMovieDetail = MutableLiveData<Event<Int>>()
    val navigateToMovieDetail: LiveData<Event<Int>> = _navigateToMovieDetail

    init {
        getOwnWatchlist()
    }

    fun getOwnWatchlist() = viewModelScope.launch {
        DataResult.loading(null)
        try {
            _movies.value = DataResult.success(repository.getWatchlistMoviesAsync())
        } catch (t: Throwable) {
            t.printStackTrace()
            DataResult.error(t.message, null)
        }
    }

    fun navigateToMovieDetail(movieId: Int) {
        _navigateToMovieDetail.value = Event(movieId)
    }
}