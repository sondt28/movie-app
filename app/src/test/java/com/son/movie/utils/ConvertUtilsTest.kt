package com.son.movie.utils

import android.content.Context
import android.content.res.Resources
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class ConvertUtilsTest {


    @Test
    fun convertRuntimeToFormatted_formatted_returnCorrectFormat() {
        val runtime = "120"

        assertEquals("120 Minutes", runtime)
    }

}