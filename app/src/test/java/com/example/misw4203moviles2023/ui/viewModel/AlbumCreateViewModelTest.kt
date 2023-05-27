package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.domain.album.CreateAlbums
import com.example.misw4203moviles2023.domain.album.model.Album
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
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class AlbumCreateViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var createAlbum: CreateAlbums

    private lateinit var viewModel: AlbumCreateViewModel

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        viewModel = AlbumCreateViewModel(app)
        viewModel.createAlbumService = createAlbum
    }

    @Test
    fun test_createAlbum() = runTest {
        // Arrange
        val album = Album(1, "Album 1", "Description 1", "2021-01-01", "Genre 1", "Record Label 1", "", emptyList())

        // Act
        viewModel.createAlbum(album)

        verify(createAlbum, times(1)).invoke(album)
    }

    @Test
    fun test_onCreateSetsGenresAndRecordLabels() = runTest {
        // Arrange
        val genres = listOf("Salsa", "Rock", "Folk", "Classical")
        val recordLabels = listOf("Sony Music", "Fania Records", "EMI", "Elektra", "Discos Fuentes")

        // Act
        viewModel.onCreate()

        // Assert
        assertEquals(genres, viewModel.genres.value)
        assertEquals(recordLabels, viewModel.recordLabels.value)
    }
}
