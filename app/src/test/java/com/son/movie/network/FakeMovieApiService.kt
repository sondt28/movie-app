package com.son.movie.network

import com.son.movie.model.BookmarkRequest
import com.son.movie.model.BookmarkResponse
import com.son.movie.model.Movie
import com.son.movie.model.Movies
import com.son.movie.model.Trailers
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class FakeMovieApiService(val fakeMovies: Movies) : MovieApiService{
    override fun getTrendingMoviesTodayAsync(apiKey: String): Deferred<Movies> {
        return CompletableDeferred(fakeMovies)
    }

    override fun getMovieDetailsAsync(movieId: Int, apiKey: String): Deferred<Movie> {
        TODO("Not yet implemented")
    }

    override fun getTrailerMovieAsync(movieId: Int, apiKey: String): Deferred<Trailers> {
        TODO("Not yet implemented")
    }

    override fun getResultSearchAsync(query: String, apiKey: String): Deferred<Movies> {
        TODO("Not yet implemented")
    }

    override fun getNowPlayingMoviesAsync(apiKey: String): Deferred<Movies> {
        TODO("Not yet implemented")
    }

    override fun getUpcomingMoviesAsync(apiKey: String): Deferred<Movies> {
        TODO("Not yet implemented")
    }

    override fun getTopRatedMoviesAsync(apiKey: String): Deferred<Movies> {
        TODO("Not yet implemented")
    }

    override fun getPopularMoviesAsync(apiKey: String): Deferred<Movies> {
        TODO("Not yet implemented")
    }

    override fun getWatchlistMoviesAsync(accountId: Int, apiKey: String): Deferred<Movies> {
        TODO("Not yet implemented")
    }

    override fun addToWatchlistAsync(
        accountId: Int,
        bookmarkRequest: BookmarkRequest,
        apiKey: String
    ): Deferred<BookmarkResponse> {
        TODO("Not yet implemented")
    }
}