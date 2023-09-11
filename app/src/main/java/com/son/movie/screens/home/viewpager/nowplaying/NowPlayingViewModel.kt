package com.son.movie.screens.home.viewpager.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class NowPlayingStatus { LOADING, DONE, ERROR }

@HiltViewModel
class NowPlayingViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _status = MutableLiveData<NowPlayingStatus>()
    val status: LiveData<NowPlayingStatus> = _status

    private val _movies = MutableLiveData<Movies>()
    val movie: LiveData<Movies> = _movies

    private val _navigateToMovieDetails = MutableLiveData<Int?>()
    val navigateToMovieDetails: LiveData<Int?> = _navigateToMovieDetails

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            _status.value = NowPlayingStatus.LOADING
            try {
                _movies.value = repository.getNowPlayingMoviesAsync()
                _status.value = NowPlayingStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = NowPlayingStatus.ERROR
            }
        }
    }

    fun navigateToMovieDetails(movieId: Int) {
        _navigateToMovieDetails.value = movieId
    }

    fun navigateToMovieDetailsComplete() {
        _navigateToMovieDetails.value = null
    }
}