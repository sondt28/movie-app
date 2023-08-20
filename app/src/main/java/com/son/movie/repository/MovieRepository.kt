package com.son.movie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.son.movie.database.DatabaseMovie
import com.son.movie.database.MovieDatabase
import com.son.movie.database.asDomainModel
import com.son.movie.model.Movie
import com.son.movie.network.MovieApi
import com.son.movie.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MovieDatabase) {

    private val dbMovies: LiveData<List<DatabaseMovie>> = database.movieDao.getMovies()
    val movies = dbMovies.map {
        it.asDomainModel()
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val movies = MovieApi.retrofitService.getTrendingMoviesTodayAsync().await()
            database.movieDao.insertAll(*movies.asDatabaseModel())
        }
    }
}