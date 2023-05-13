package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.misw4203moviles2023.domain.album.GetAlbumById
import com.example.misw4203moviles2023.test.TestApplication
import kotlinx.coroutines.test.runTest
import mockAlbumModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class AlbumDetailViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getAlbumById: GetAlbumById

    private lateinit var viewModel: AlbumDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = AlbumDetailViewModel()
        viewModel.getAlbumById = getAlbumById
    }

    @Test
    fun testOnCreate() = runTest {
        val albumId = 1
        val releaseDate = "2023-05-09T10:00:00Z"
        val album = mockAlbumModel(
            albumId,
            "Album Title",
            "Artist Name",
            releaseDate,
            "https://example.com/album-cover.jpg",
            "Metal",
            "Album Studio",
        )

        // Set up the mocked result
        `when`(viewModel.getAlbumById(albumId)).thenReturn(album)

        // Call the method under test
        viewModel.onCreate(albumId)

        // Verify that the albumModel is set correctly
        assertEquals(album, viewModel.albumModel.value)
    }
}
