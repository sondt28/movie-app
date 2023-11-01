package com.son.movie.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.son.movie.model.Movies
import com.son.movie.repository.MovieRepository
import com.son.movie.utils.DataResult
import com.son.movie.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
    private val _searchMovie = MutableLiveData<String>()

    var result = _searchMovie.switchMap { query ->
        repository.getResultSearchAsync(query).cachedIn(viewModelScope)
    }

    private val _resultSearch = MutableLiveData<DataResult<Movies>>()
    val resultSearch: LiveData<DataResult<Movies>> = _resultSearch

    private val _navigateToMovieDetails = MutableLiveData<Event<Int>>()
    val navigateToMovieDetails: LiveData<Event<Int>> = _navigateToMovieDetails

    fun searchMovieChanged(query: String) {
        _searchMovie.value = query
    }

    fun displayDetailsFilm(movieId: Int) {
        _navigateToMovieDetails.value = Event(movieId)
    }
}