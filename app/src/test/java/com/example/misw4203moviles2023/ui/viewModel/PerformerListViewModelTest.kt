package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.domain.performer.GetPerformers
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
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class PerformerListViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPerformers: GetPerformers

    private lateinit var viewModel: PerformerListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        viewModel = PerformerListViewModel(app)
        viewModel.getPerformers = getPerformers
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testOnCreate() = runTest {
        val performerId = 1
        val releaseDate = "2023-05-09T10:00:00Z"
        val performers = listOf(
            mockPerformer(
                performerId,
                "Performer Title2",
                "Artist Name2",
                releaseDate,
            ),
            mockPerformer(
                performerId,
                "Performer Title2",
                "Artist Name2",
                releaseDate,
            ),
        )

        // Set up the mocked result
        `when`(viewModel.getPerformers()).thenReturn(performers)

        // Call the method under test
        viewModel.onCreate()

        // Verify that the performerModel is set correctly
        assertEquals(performers, viewModel.performerModel.value)
    }
}
