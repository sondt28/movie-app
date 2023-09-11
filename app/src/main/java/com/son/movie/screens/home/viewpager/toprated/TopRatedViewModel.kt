package com.son.movie.screens.home.viewpager.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class TopRatedStatus { LOADING, DONE, ERROR }

@HiltViewModel
class TopRatedViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _status = MutableLiveData<TopRatedStatus>()
    val status: LiveData<TopRatedStatus> = _status

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies> = _movies

    private val _navigateToMovieDetails = MutableLiveData<Int?>()
    val navigateToMovieDetails: LiveData<Int?> = _navigateToMovieDetails

    init {
        getUpcomingMovies()
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            _status.value = TopRatedStatus.LOADING
            try {
                _movies.value = repository.getTopRatedMoviesAsync()
                _status.value = TopRatedStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = TopRatedStatus.ERROR
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