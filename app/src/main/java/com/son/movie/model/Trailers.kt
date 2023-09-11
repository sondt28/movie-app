package com.son.movie.model

import com.squareup.moshi.Json

data class Trailer(
    val type: String,
    val key: String
)

data class Trailers(
    @Json(name = "results")
    val resultVideoMovie: List<Trailer>
)