package com.example.misw4203moviles2023.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AlbumModelTest {

    @Test
    fun testAlbumModelProperties() {
        // Create an instance of the class under test
        val album = AlbumModel(
            id = 1,
            name = "Album Name",
            cover = "https://example.com/album-cover.jpg",
            releaseDate = "2023-05-01",
            description = "Album description",
            genre = "Rock",
            recordLabel = "Record label",
            tracks = emptyList(),
        )

        // Assert that the properties of the object are set correctly
        assertEquals(1, album.id)
        assertEquals("Album Name", album.name)
        assertEquals("https://example.com/album-cover.jpg", album.cover)
        assertEquals("2023-05-01", album.releaseDate)
        assertEquals("Album description", album.description)
        assertEquals("Rock", album.genre)
        assertEquals("Record label", album.recordLabel)
        assertTrue(album.tracks.isEmpty())
    }

    @Test
    fun testAlbumModelToString() {
        // Create an instance of the class under test
        val album = AlbumModel(
            id = 1,
            name = "Album Name",
            cover = "https://example.com/album-cover.jpg",
            releaseDate = "2023-05-01",
            description = "Album description",
            genre = "Rock",
            recordLabel = "Record label",
            tracks = emptyList(),
        )

        // Call the method under test
        val result = album.toString()

        // Assert that the result is in the expected format
        val expected =
            "AlbumModel(id=1, name=Album Name, cover=https://example.com/album-cover.jpg, " +
                "releaseDate=2023-05-01, description=Album description, genre=Rock, " +
                "recordLabel=Record label, tracks=[])"
        assertEquals(expected, result)
    }
}
