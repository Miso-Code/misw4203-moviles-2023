package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.domain.album.AddTrackToAlbum
import com.example.misw4203moviles2023.domain.album.GetAlbumById
import com.example.misw4203moviles2023.mockAlbum
import com.example.misw4203moviles2023.mockTrack
import com.example.misw4203moviles2023.test.TestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class AlbumAddTrackViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var getAlbumById: GetAlbumById

    @Mock
    lateinit var addTrack: AddTrackToAlbum

    private lateinit var viewModel: AlbumAddTrackViewModel

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        viewModel = AlbumAddTrackViewModel(app)
        viewModel.getAlbumById = getAlbumById
        viewModel.addTrack = addTrack
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_getAlbumById() = runTest {
        // Arrange
        val album = mockAlbum(
            1,
            "Album Title",
            "Artist Name",
            "2021-01-01",
            "https://example.com/album-cover.jpg",
            "Metal",
            "Album Studio",
        )
        `when`(getAlbumById.invoke(album.id)).thenReturn(album)

        // Act
        viewModel.onCreate(album.id)

        // Assert
        assertEquals(album, viewModel.albumModel.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_addTrack() = runTest {
        // Arrange
        val album = mockAlbum(
            1,
            "Album Title",
            "Artist Name",
            "2021-01-01",
            "https://example.com/album-cover.jpg",
            "Metal",
            "Album Studio",
        )
        val track = mockTrack(
            1,
            "Track Title",
            "Track Duration",
        )
        `when`(getAlbumById.invoke(album.id)).thenReturn(album)
        `when`(addTrack.invoke(album.id, track)).thenReturn(Unit)

        // Act
        viewModel.onCreate(album.id)
        viewModel.addTrack(track)

        // Assert
        verify(addTrack, times(1)).invoke(album.id, track)
    }
}
