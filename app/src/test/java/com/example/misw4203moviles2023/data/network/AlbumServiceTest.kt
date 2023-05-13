package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.test.TestApplication
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class AlbumServiceTest {
    private lateinit var albumService: AlbumService
    private lateinit var apiClient: AlbumApiClient

    @Before
    fun setUp() {
        apiClient = mock(AlbumApiClient::class.java)
        albumService = AlbumService(apiClient)
    }

    @Test
    fun testGetAlbums() = runTest {
        // Given
        val albums = listOf(
            AlbumModel(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList()),
            AlbumModel(2, "Album 2", "cover2.jpg", "2022-02-01", "", "", "", emptyList()),
        )
        whenever(apiClient.getAllAlbums()).thenReturn(Response.success(albums))

        // When
        val result = albumService.getAlbums()

        // Then
        assertEquals(albums, result)
    }

    @Test
    fun testGetAlbumById() = runTest {
        // Given
        val album = AlbumModel(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList())
        whenever(apiClient.getAlbumById(album.id)).thenReturn(Response.success(album))

        // When
        val result = albumService.getAlbumById(album.id)

        // Then
        assertEquals(album, result)
    }
}
