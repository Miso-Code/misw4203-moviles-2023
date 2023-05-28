package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.domain.collector.GetCollectors
import com.example.misw4203moviles2023.mockCollector
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

@OptIn(ExperimentalCoroutinesApi::class)
@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class CollectorListViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCollectors: GetCollectors

    private lateinit var viewModel: CollectorListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        viewModel = CollectorListViewModel(app)
        viewModel.getCollectors = getCollectors
    }

    @Test
    fun testOnCreate() = runTest {
        val collectorId = 1
        val releaseDate = "2023-05-09T10:00:00Z"
        val collectors = listOf(
            mockCollector(
                collectorId,
                "Collector Title2",
                "Artist Name2",
                releaseDate,
            ),
            mockCollector(
                collectorId,
                "Collector Title2",
                "Artist Name2",
                releaseDate,
            ),
        )

        // Set up the mocked result
        `when`(viewModel.getCollectors()).thenReturn(collectors)

        // Call the method under test
        viewModel.onCreate()

        // Verify that the collectorModel is set correctly
        assertEquals(collectors, viewModel.collectorModel.value)
    }
}
