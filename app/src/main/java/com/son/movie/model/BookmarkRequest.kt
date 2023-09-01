package com.son.movie.model

import com.squareup.moshi.Json

data class BookmarkRequest(
    @Json(name = "media_id")
    val mediaId: Int,
    @Json(name = "media_type")
    val mediaType: String = "movie",
    val watchlist: Boolean?
)
