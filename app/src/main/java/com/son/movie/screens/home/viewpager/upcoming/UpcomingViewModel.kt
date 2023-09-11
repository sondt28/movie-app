package com.son.movie.screens.home.viewpager.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class UpcomingStatus { LOADING, DONE, ERROR }

@HiltViewModel
class UpcomingViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val _status = MutableLiveData<UpcomingStatus>()
    val status: LiveData<UpcomingStatus> = _status

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies> = _movies

    private val _navigateToMovieDetails = MutableLiveData<Int?>()
    val navigateToMovieDetails: LiveData<Int?> = _navigateToMovieDetails

    init {
        getUpcomingMovies()
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            _status.value = UpcomingStatus.LOADING
            try {
                _movies.value = repository.getUpcomingMoviesAsync()
                _status.value = UpcomingStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = UpcomingStatus.ERROR
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