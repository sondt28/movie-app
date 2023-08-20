package com.son.movie.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.son.movie.model.Movie

@Entity
data class DatabaseMovie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String
)

fun List<DatabaseMovie>.asDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath
        )
    }
}
