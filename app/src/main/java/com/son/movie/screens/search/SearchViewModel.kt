package com.son.movie.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.son.movie.model.Movies
import com.son.movie.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class SearchResultStatus { LOADING, ERROR, DONE }
class SearchViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _status = MutableLiveData<SearchResultStatus>()
    val status: LiveData<SearchResultStatus>
        get() = _status

    private val _resultSearch = MutableLiveData<Movies>()
    val resultSearch: LiveData<Movies>
        get() = _resultSearch

    private val _navigateToMovieDetails = MutableLiveData<Int?>()
    val navigateToMovieDetails: LiveData<Int?>
        get() = _navigateToMovieDetails

    fun getSearchResult(query: String) {
        coroutineScope.launch {
            Dispatchers.IO
            _status.value = SearchResultStatus.LOADING
            try {
                val searchResultDeferred = MovieApi.retrofitService.getResultSearchAsync(query)
                _resultSearch.value = searchResultDeferred.await()
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}