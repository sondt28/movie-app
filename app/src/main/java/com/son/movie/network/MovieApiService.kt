package com.son.movie.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.son.movie.model.BookmarkRequest
import com.son.movie.model.BookmarkResponse
import com.son.movie.model.Movie
import com.son.movie.model.Trailers
import com.son.movie.model.Movies
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "ed195fd3f2bad65d5c2428ff9cba3d7f"
const val ACCOUNT_ID = 20257678
private const val BEARER_TOKEN =
    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZDE5NWZkM2YyYmFkNjVkNWMyNDI4ZmY5Y2JhM2Q3ZiIsInN1YiI6IjY0Y2UyM2E0MzAzYzg1MDEzYTE0YTA5NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FCAwwe75np_IHRV3NgtChg00dqx0A-WrVaVuWRO1VSA"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

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

object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}