package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.performer.GetPerformerById
import com.example.misw4203moviles2023.domain.performer.model.Performer
import com.example.misw4203moviles2023.mockPerformer
import com.example.misw4203moviles2023.test.TestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class PerformerDetailViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var getPerformerById: GetPerformerById

    private lateinit var viewModel: PerformerDetailViewModel

    @Before
    fun setUp() {
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        viewModel = PerformerDetailViewModel(app)
        viewModel.getPerformerById = getPerformerById
    }

    @Test
    fun testOnCreate() = runTest {
        val performerId = 1
        val performer = mockPerformer(
            performerId,
            "Performer Title",
            "Performer description",
            "performer.png",
        )

        // Set up the mocked result
        `when`(viewModel.getPerformerById(1)).thenReturn(performer)

        // Call the method under test
        viewModel.onCreate(performerId)

        // Verify that the performerModel is set correctly
        assertEquals(performer, viewModel.performerModel.value)
    }

    @Test
    fun test_shouldParseDates() = runTest {
        // Arrange
        val performerId = 1
        val performer = Performer(
            performerId,
            "Performer Title",
            "Performer description",
            "performer.png",
            listOf(
                Album(
                    1,
                    "Album 1",
                    "Description 1",
                    "1984-08-01T00:00:00.000Z",
                    "Genre 1",
                    "Record Label 1",
                    "EMI",
                    emptyList(),
                ),
            ),
        )

        // Set up the mocked result
        `when`(viewModel.getPerformerById(1)).thenReturn(performer)

        // Act
        viewModel.onCreate(performerId)

        // Assert
        assertEquals(
            "01/08/1984",
            viewModel.performerModel.value?.albums?.first()?.releaseDate,
        )
    }
}
