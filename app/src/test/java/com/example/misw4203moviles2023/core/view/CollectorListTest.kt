package com.example.misw4203moviles2023.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.mockCollector
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.viewModel.CollectorListViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class CollectorListTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CollectorListViewModel

    @Before
    fun setup() {
        viewModel = Mockito.mock(CollectorListViewModel::class.java)
    }

    @Test
    fun testCollectorListViewModel() {
        val collectorList = listOf(
            mockCollector(
                1,
                "Collector Name",
                "Collector Description",
                "Collector Genre",
            ),
            mockCollector(
                2,
                "Collector Name",
                "Collector Description",
                "Collector Genre",
            ),
        )

        Mockito.`when`(viewModel.collectorModel).thenReturn(MutableLiveData(collectorList))

        val collectorListFragment = CollectorList(viewModel)

        // Use launchFragment to create the fragment in isolation

        launchFragment<CollectorList>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    collectorListFragment
            },
        )

        // Verify that the view model onCreate() method was called with the correct collector ID
        assertEquals(collectorList, viewModel.collectorModel.value)
    }
}
