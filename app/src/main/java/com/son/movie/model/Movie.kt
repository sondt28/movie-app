package com.son.movie.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class Movie(
    val id: Int,
    val title: String,
    @Json(name = "poster_path")
    val posterPath: String
)
