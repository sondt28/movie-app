package com.son.movie.utils

import android.content.res.Resources
import com.son.movie.R

fun convertRuntimeToFormatted(runtime: Int, res: Resources): String {
    return res.getString(R.string.runtime, runtime)
}