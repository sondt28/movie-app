package com.son.movie.network

import com.son.movie.model.BookmarkRequest
import com.son.movie.model.BookmarkResponse
import com.son.movie.model.Movie
import com.son.movie.model.Trailers
import com.son.movie.model.Movies
import com.son.movie.utils.Constant.ACCOUNT_ID
import com.son.movie.utils.Constant.API_KEY
import com.son.movie.utils.Constant.BEARER_TOKEN
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("trending/movie/day")
    fun getTrendingMoviesTodayAsync(@Query("api_key") apiKey: String = API_KEY): Deferred<Movies>

    @GET("movie/{movieId}")
    fun getMovieDetailsAsync(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<Movie>

    @GET("movie/{movieId}/videos")
    fun getTrailerMovieAsync(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<Trailers>

    @GET("search/movie")
    fun getResultSearchAsync(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<Movies>

    @GET("movie/now_playing")
    fun getNowPlayingMoviesAsync(@Query("api_key") apiKey: String = API_KEY): Deferred<Movies>

    @GET("movie/upcoming")
    fun getUpcomingMoviesAsync(@Query("api_key") apiKey: String = API_KEY): Deferred<Movies>

    @GET("movie/top_rated")
    fun getTopRatedMoviesAsync(@Query("api_key") apiKey: String = API_KEY): Deferred<Movies>

    @GET("movie/popular")
    fun getPopularMoviesAsync(@Query("api_key") apiKey: String = API_KEY): Deferred<Movies>

    @Headers("Authorization: $BEARER_TOKEN")
    @GET("account/{accountId}/watchlist/movies")
    fun getWatchlistMoviesAsync(
        @Path("accountId") accountId: Int = ACCOUNT_ID,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<Movies>

    @POST("account/{accountId}/watchlist")
    @Headers("Authorization: $BEARER_TOKEN")
    fun addToWatchlistAsync(
        @Path("accountId") accountId: Int = ACCOUNT_ID,
        @Body bookmarkRequest: BookmarkRequest,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<BookmarkResponse>
}