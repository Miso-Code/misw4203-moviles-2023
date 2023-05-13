package com.example.misw4203moviles2023.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class TrackModelTest {

    @Test
    fun testTrackModelProperties() {
        // Create an instance of the class under test
        val track = TrackModel(
            id = 1,
            name = "Track Name",
            duration = "3:30",
        )

        // Assert that the properties of the object are set correctly
        assertEquals(1, track.id)
        assertEquals("Track Name", track.name)
        assertEquals("3:30", track.duration)
    }

    @Test
    fun testTrackModelToString() {
        // Create an instance of the class under test
        val track = TrackModel(
            id = 1,
            name = "Track Name",
            duration = "3:30",
        )

        // Call the method under test
        val result = track.toString()

        // Assert that the result is in the expected format
        val expected = "TrackModel(id=1, name=Track Name, duration=3:30)"
        assertEquals(expected, result)
    }
}
