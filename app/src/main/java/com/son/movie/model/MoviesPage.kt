package com.son.movie.model

data class MoviesPage(
    val page: Int,
    val results: List<Movie>
)
