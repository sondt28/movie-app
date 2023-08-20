package com.son.movie.model

import com.squareup.moshi.Json

data class MovieTrailer(
    val type: String,
    val key: String
)

data class ResultMovieVideo(
    @Json(name = "results")
    val resultVideoMovie: List<MovieTrailer>
)