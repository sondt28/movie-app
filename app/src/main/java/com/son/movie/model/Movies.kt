package com.son.movie.model

import com.squareup.moshi.Json

data class Movies(
    val page: Int,
    val results: List<Movie>,
    @Json(name = "total_pages")
    val totalPage: Int
)
