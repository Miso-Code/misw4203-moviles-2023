package com.example.misw4203moviles2023.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.misw4203moviles2023.domain.collector.GetCollectorById
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
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class CollectorDetailViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var getCollectorById: GetCollectorById

    private lateinit var viewModel: CollectorDetailViewModel

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        viewModel = CollectorDetailViewModel(app)
        viewModel.getCollectorById = getCollectorById
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testOnCreate() = runTest {
        val collectorId = 1
        val email = "2023-05-09T10:00:00Z"
        val collector = mockCollector(
            collectorId,
            "Collector Title",
            "Artist Name",
            email,
        )

        // Set up the mocked result
        `when`(viewModel.getCollectorById(1)).thenReturn(collector)

        // Call the method under test
        viewModel.onCreate(collectorId)

        // Verify that the collectorModel is set correctly
        assertEquals(collector, viewModel.collectorModel.value)
    }
}
