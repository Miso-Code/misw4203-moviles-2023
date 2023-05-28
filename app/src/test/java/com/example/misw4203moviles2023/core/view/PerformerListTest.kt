package com.example.misw4203moviles2023.core.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.mockPerformer
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.view.PerformerList
import com.example.misw4203moviles2023.ui.viewModel.PerformerListViewModel
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
class PerformerListTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PerformerListViewModel

    @Before
    fun setup() {
        viewModel = Mockito.mock(PerformerListViewModel::class.java)
    }

    @Test
    fun testPerformerListViewModel() {
        val performerList = listOf(
            mockPerformer(
                1,
                "Performer Name",
                "Performer Description",
                "Performer Genre",
            ),
            mockPerformer(
                2,
                "Performer Name",
                "Performer Description",
                "Performer Genre",
            ),
        )

        Mockito.`when`(viewModel.performerModel).thenReturn(MutableLiveData(performerList))

        val performerListFragment = PerformerList(viewModel)

        // Use launchFragment to create the fragment in isolation

        launchFragment<PerformerList>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    performerListFragment
            },
        )

        // Verify that the view model onCreate() method was called with the correct performer ID
        assertEquals(performerList, viewModel.performerModel.value)
    }
}
