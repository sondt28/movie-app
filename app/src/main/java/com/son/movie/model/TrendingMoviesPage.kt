package com.son.movie.model

data class TrendingMoviesPage(
    val page: Int,
    val results: List<Movie>
)
