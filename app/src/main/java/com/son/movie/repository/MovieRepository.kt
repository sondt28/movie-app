package com.son.movie.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.son.movie.model.BookmarkRequest
import com.son.movie.model.BookmarkResponse
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.model.Trailers

interface MovieRepository {
    suspend fun getTrendingMovieTodayAsync(): Movies
    suspend fun getMovieDetailsAsync(movieId: Int): Movie
    suspend fun getMovieVideosAsync(movieId: Int): Trailers
    fun getResultSearchAsync(query: String): LiveData<PagingData<Movie>>
    suspend fun getNowPlayingMoviesAsync(): Movies
    suspend fun getUpcomingMoviesAsync(): Movies
    suspend fun getTopRatedMoviesAsync(): Movies
    suspend fun getPopularMoviesAsync(): Movies
    suspend fun getWatchlistMoviesAsync(): Movies
    suspend fun addToWatchlistAsync(bookmarkRequest: BookmarkRequest): BookmarkResponse
}