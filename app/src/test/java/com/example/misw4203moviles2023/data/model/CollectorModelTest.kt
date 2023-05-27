package com.example.misw4203moviles2023.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class CollectorModelTest {

    @Test
    fun testCollectorModelProperties() {
        // Create an instance of the class under test
        val collector = CollectorModel(
            id = 1,
            name = "Collector Name",
            telephone = "330898",
            email = "collector@email.com",
        )

        // Assert that the properties of the object are set correctly
        assertEquals(1, collector.id)
        assertEquals("Collector Name", collector.name)
        assertEquals("330898", collector.telephone)
        assertEquals("collector@email.com", collector.email)
    }

    @Test
    fun testCollectorModelToString() {
        // Create an instance of the class under test
        val collector = CollectorModel(
            id = 1,
            name = "Collector Name",
            telephone = "330898",
            email = "collector@email.com",
            albums = emptyList(),
            performers = emptyList(),
        )

        // Call the method under test
        val result = collector.toString()

        // Assert that the result is in the expected format
        val expected =
            "CollectorModel(id=1, name=Collector Name, telephone=330898, " +
                "email=collector@email.com, albums=[], performers=[])"
        assertEquals(expected, result)
    }
}
