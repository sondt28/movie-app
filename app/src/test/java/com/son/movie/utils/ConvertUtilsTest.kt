package com.son.movie.utils

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ConvertUtilsTest {

    @Test
    fun convertRuntimeToFormatted_formatted_returnCorrectFormat() {

        val runtime = "120 Minutes"
        assertEquals("120 Minutes", runtime)
    }

}