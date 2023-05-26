package com.example.misw4203moviles2023.data

import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.AlbumModelCreate
import com.example.misw4203moviles2023.data.model.AlbumModelNoTracks
import com.example.misw4203moviles2023.data.model.TrackModel
import com.example.misw4203moviles2023.data.model.TrackModelCreate
import com.example.misw4203moviles2023.data.network.AlbumService
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.toDomain
import com.example.misw4203moviles2023.test.TestApplication
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class AlbumRepositoryTest {
    private lateinit var repository: AlbumRepository

    @Mock
    private lateinit var mockService: AlbumService

    @Before
    @Test
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mockService = mock(AlbumService::class.java)
        repository = AlbumRepository(mockService, context)
    }

    @Test
    fun `getAllAlbums should return empty list when api returns null`() = runTest {
        // Given
        whenever(mockService.getAlbums()).thenReturn(emptyList())

        // When
        val result = repository.getAllAlbumsFromApi()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllAlbums should return list of albums when api returns non-null response`() = runTest {
        // Given
        val albumsModel = listOf(
            AlbumModel(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList()),
            AlbumModel(2, "Album 2", "cover2.jpg", "2022-02-01", "", "", "", emptyList()),
        )
        whenever(mockService.getAlbums()).thenReturn(albumsModel)

        val albums = listOf(
            Album(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList()),
            Album(2, "Album 2", "cover2.jpg", "2022-02-01", "", "", "", emptyList()),
        )

        // When
        val result = repository.getAllAlbumsFromApi()

        // Then
        assertEquals(albums, result)
    }

    @Test
    fun `getAlbumById should return default album when api returns null`() = runTest {
        // Given
        val id = 1
        val defaultAlbumModel = AlbumModel(0, "", "", "", "", "", "", emptyList())
        whenever(mockService.getAlbumById(id)).thenReturn(defaultAlbumModel)

        // When
        val result = repository.getAlbumByIdFromApi(id)
        val defaultAlbum = Album(0, "", "", "", "", "", "", emptyList())

        // Then
        assertEquals(defaultAlbum, result)
    }

    @Test
    fun `getAlbumById should return album when api returns non-null response`() = runTest {
        // Given
        val id = 1
        val albumModel =
            AlbumModel(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList())
        whenever(mockService.getAlbumById(id)).thenReturn(albumModel)

        // When
        val album = Album(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList())
        val result = repository.getAlbumByIdFromApi(id)

        // Then
        assertEquals(album, result)
    }

    @Test
    fun `createAlbum should return album when api returns non-null response`() = runTest {
        // Given
        val albumCreate = AlbumModelCreate(
            "Album 1",
            "cover1.jpg",
            "2022-01-01",
            "",
            "",
            "",
        )
        val albumModel = AlbumModelNoTracks(
            1,
            albumCreate.name,
            albumCreate.cover,
            albumCreate.releaseDate,
            albumCreate.description,
            albumCreate.genre,
            albumCreate.recordLabel,
        )
        whenever(mockService.createAlbum(albumCreate)).thenReturn(albumModel)

        // When
        val album = albumModel.toDomain()
        val result = repository.createAlbumToToApi(album)

        // Then
        assertEquals(album, result)
    }

    @Test
    fun `addTrack should return a track when api returns non-null response`() = runTest {
        // Given
        val albumModel =
            AlbumModel(1, "Album 1", "cover1.jpg", "2022-01-01", "", "", "", emptyList())
        val trackCreate = TrackModelCreate("Track 1", "2022-01-01")

        val track = TrackModel(1, trackCreate.name, trackCreate.duration)

        whenever(mockService.addTrackToAlbum(albumModel.id, trackCreate)).thenReturn(track)

        // When
        val album = albumModel.toDomain()
        val result = repository.addTrackToAlbumApi(album.id, track.toDomain())

        // Then
        assertEquals(track.toDomain(), result)
    }
}
