package dev.baharudin.tmdb_android.core.data.models

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ArrayListOfIntConverterTest {

    private lateinit var converter: ArrayListOfIntConverter

    @Before
    fun setUp() {
        converter = ArrayListOfIntConverter()
    }

    @Test
    fun fromIntArrayListToString() {
        // Arrange
        val input = arrayListOf(1,231,2412,4)
        val expectedOutput = "[1,231,2412,4]"

        // Act
        val output = converter.fromArrayListToString(input)

        // Assert
        assertEquals(expectedOutput, output)
    }

    @Test
    fun fromStringToArrayList() {
        // Arrange
        val input = "[1,231,2412,4]"
        val expectedOutput = arrayListOf(1,231,2412,4)

        // Act
        val output = converter.fromStringToArrayList(input)

        // Assert
        assertEquals(expectedOutput, output)
    }
}