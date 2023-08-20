package com.son.movie.network

import com.son.movie.database.DatabaseMovie
import com.son.movie.model.Movie
import com.son.movie.model.Movies

fun Movies.asDomainModel(): List<Movie> {
    return results.map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath
        )
    }
}

fun Movies.asDatabaseModel(): Array<DatabaseMovie> {
    return results.map {
        DatabaseMovie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath
        )
    }.toTypedArray()
}