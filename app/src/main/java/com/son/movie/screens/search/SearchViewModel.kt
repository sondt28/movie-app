package com.son.movie.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.son.movie.model.Movies
import com.son.movie.network.MovieApi
import com.son.movie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SearchResultStatus { LOADING, ERROR, DONE }
@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _status = MutableLiveData<SearchResultStatus>()
    val status: LiveData<SearchResultStatus> = _status

    private val _resultSearch = MutableLiveData<Movies>()
    val resultSearch: LiveData<Movies> = _resultSearch

    private val _navigateToMovieDetails = MutableLiveData<Int?>()
    val navigateToMovieDetails: LiveData<Int?> = _navigateToMovieDetails

    fun getSearchResult(query: String) {
        viewModelScope.launch {
            _status.value = SearchResultStatus.LOADING
            try {
                _resultSearch.value = repository.getResultSearchAsync(query)
                _status.value = SearchResultStatus.DONE
            } catch (t: Throwable) {
                t.printStackTrace()
                _status.value = SearchResultStatus.ERROR
            }
        }
    }
    fun displayDetailsFilm(movieId: Int) {
        _navigateToMovieDetails.value = movieId
    }
    fun displayDetailsFilmComplete() {
        _navigateToMovieDetails.value = null
    }
}