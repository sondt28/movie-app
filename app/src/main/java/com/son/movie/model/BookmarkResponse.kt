package com.son.movie.model

import com.squareup.moshi.Json

data class BookmarkResponse(
    val success: Boolean,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)