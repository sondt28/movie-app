package com.son.movie.model

import com.squareup.moshi.Json
data class Movie(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val overview: String,
    val runtime: Int = 0,
    val genres: List<Genre>?,
    @Json(name = "vote_average")
    val voteAverage: Float
)
