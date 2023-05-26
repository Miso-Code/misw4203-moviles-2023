package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.AlbumModelCreate
import com.example.misw4203moviles2023.data.model.AlbumModelNoTracks
import com.example.misw4203moviles2023.data.model.TrackModel
import com.example.misw4203moviles2023.data.model.TrackModelCreate
import com.example.misw4203moviles2023.test.TestApplication
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
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

    @Test
    fun testCreateAlbum() = runTest {
        // Given
        val album = AlbumModelCreate(
            "Album 1",
            "cover1.jpg",
            "2022-01-01",
            "",
            "",
            "",
        )
        val albumModel = AlbumModelNoTracks(
            1,
            album.name,
            album.cover,
            album.releaseDate,
            album.description,
            album.genre,
            album.description,
        )

        whenever(apiClient.createAlbum(album)).thenReturn(Response.success(albumModel))

        // When
        val result = albumService.createAlbum(album)

        // Then assert the result is ok
        assertEquals(albumModel, result)
    }

    @Test
    fun testDeleteAlbum() = runTest {
        // Given
        val album = AlbumModelNoTracks(
            1,
            "Album 1",
            "cover1.jpg",
            "2022-01-01",
            "",
            "",
            "",
        )
        whenever(apiClient.deleteAlbumById(album.id)).thenReturn(Response.success(Unit))

        // When
        val result = albumService.deleteAlbumById(album.id)

        // Then assert the result is ok
        assertEquals(Unit, result)
    }

    @Test
    fun testAddTrackToAlbum() = runTest {
        // Given
        val album = AlbumModel(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList())
        val track = TrackModelCreate("Track 1", "track1.mp3")

        val trackModel = TrackModel(1, track.name, track.duration)
        whenever(
            apiClient.addTrackToAlbum(
                album.id,
                track,
            ),
        ).thenReturn(Response.success(trackModel))

        // When
        val result = albumService.addTrackToAlbum(album.id, track)

        // Then assert the result is ok
        assertEquals(trackModel, result)
    }

    @Test
    fun testDeleteTrackFromAlbum() = runTest {
        // Given
        val album = AlbumModel(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList())
        val track = TrackModel(1, "Track 1", "track1.mp3")

        whenever(
            apiClient.deleteTrackFromAlbum(
                album.id,
                track.id,
            ),
        ).thenReturn(Response.success(Unit))

        // When
        val result = albumService.deleteTrackFromAlbum(album.id, track.id)

        // Then assert the result is ok
        assertEquals(Unit, result)
    }
}
