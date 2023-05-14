package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.domain.album.GetAlbums
import com.example.misw4203moviles2023.mockAlbum
import com.example.misw4203moviles2023.test.TestApplication
import kotlinx.coroutines.test.runTest
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
class AlbumListViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getAlbums: GetAlbums

    private lateinit var viewModel: AlbumListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        viewModel = AlbumListViewModel(app)
        viewModel.getAlbums = getAlbums
    }

    @Test
    fun testOnCreate() = runTest {
        val albumId = 1
        val releaseDate = "2023-05-09T10:00:00Z"
        val albums = listOf(
            mockAlbum(
                albumId,
                "Album Title2",
                "Artist Name2",
                releaseDate,
                "https://example.com/album-cover.jpg",
                "Metal",
                "Album Studio",
            ),
            mockAlbum(
                albumId,
                "Album Title2",
                "Artist Name2",
                releaseDate,
                "https://example.com/album-cover.jpg",
                "Metal",
                "Album Studio",
            ),
        )

        // Set up the mocked result
        `when`(viewModel.getAlbums()).thenReturn(albums)

        // Call the method under test
        viewModel.onCreate()

        // Verify that the albumModel is set correctly
        assertEquals(albums, viewModel.albumModel.value)
    }
}
