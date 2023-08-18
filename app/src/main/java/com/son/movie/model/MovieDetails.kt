package com.son.movie.model

import com.squareup.moshi.Json

data class MovieDetails(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: String,
    val runtime: Int,
)
