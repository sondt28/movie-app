package com.son.movie.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.son.movie.model.MovieDetails
import com.son.movie.model.ResultMovieVideo
import com.son.movie.model.MoviesPage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "ed195fd3f2bad65d5c2428ff9cba3d7f"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

interface MovieApiService {
    @GET("trending/movie/day")
    fun getTrendingMoviesTodayAsync(@Query("api_key") apiKey: String = API_KEY): Deferred<MoviesPage>

    @GET("movie/{movieId}")
    fun getMovieDetailsAsync(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<MovieDetails>

    @GET("movie/{movieId}/videos")
    fun getTrailerMovieAsync(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<ResultMovieVideo>

    @GET("search/movie")
    fun getResultSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Deferred<MoviesPage>
}

object MovieApi {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}