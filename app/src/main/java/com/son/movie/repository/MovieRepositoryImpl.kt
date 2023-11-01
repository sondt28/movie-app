package com.son.movie.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.son.movie.model.BookmarkRequest
import com.son.movie.model.BookmarkResponse
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.model.Trailers
import com.son.movie.network.MovieApiService
import com.son.movie.paging.MoviePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApi: MovieApiService) :
    MovieRepository {
    override suspend fun getTrendingMovieTodayAsync(): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.getTrendingMoviesTodayAsync().await()
        }
    }

    override suspend fun getMovieDetailsAsync(movieId: Int): Movie {
        return withContext(Dispatchers.IO) {
            movieApi.getMovieDetailsAsync(movieId).await()
        }
    }

    override suspend fun getMovieVideosAsync(movieId: Int): Trailers {
        return withContext(Dispatchers.IO) {
            movieApi.getTrailerMovieAsync(movieId).await()
        }
    }

    override fun getResultSearchAsync(query: String): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(movieApi, query) }).liveData
    }

    override suspend fun getNowPlayingMoviesAsync(): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.getNowPlayingMoviesAsync().await()
        }
    }

    override suspend fun getUpcomingMoviesAsync(): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.getUpcomingMoviesAsync().await()
        }
    }

    override suspend fun getTopRatedMoviesAsync(): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.getTopRatedMoviesAsync().await()
        }
    }

    override suspend fun getPopularMoviesAsync(): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.getPopularMoviesAsync().await()
        }
    }

    override suspend fun getWatchlistMoviesAsync(): Movies {
        return withContext(Dispatchers.IO) {
            movieApi.getWatchlistMoviesAsync().await()
        }
    }

    override suspend fun addToWatchlistAsync(bookmarkRequest: BookmarkRequest): BookmarkResponse {
        return withContext(Dispatchers.IO) {
            movieApi.addToWatchlistAsync(bookmarkRequest = bookmarkRequest).await()
        }
    }
}