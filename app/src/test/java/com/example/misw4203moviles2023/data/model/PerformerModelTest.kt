package com.example.misw4203moviles2023.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PerformerModelTest {

    @Test
    fun testPerformerModelProperties() {
        // Create an instance of the class under test
        val performer = PerformerModel(
            id = 1,
            name = "Performer Name",
            description = "3:30",
            image = "image.png",
            emptyList(),
        )

        // Assert that the properties of the object are set correctly
        assertEquals(1, performer.id)
        assertEquals("Performer Name", performer.name)
        assertEquals("3:30", performer.description)
        assertEquals("image.png", performer.image)
    }

    @Test
    fun testPerformerModelToString() {
        // Create an instance of the class under test
        val performer = PerformerModel(
            id = 1,
            name = "Performer Name",
            description = "3:30",
            image = "image.png",
            emptyList(),
        )

        // Call the method under test
        val result = performer.toString()

        // Assert that the result is in the expected format
        val expected = "PerformerModel(id=1, name=Performer Name, description=3:30, image=image.png, albums=[])"
        assertEquals(expected, result)
    }
}
