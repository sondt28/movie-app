package com.son.movie.utils

import android.content.res.Resources
import com.son.movie.R

fun convertRuntimeToFormatted(runtime: Int, res: Resources): String {
    return res.getString(R.string.runtime, runtime)
}


fun convertReleaseToFormatted(release: String): String {
    return release.substringBefore("-")
}

fun convertVoteScoreFormatted(voteScore: Float): String {
    return String.format("%.1f", voteScore)
}