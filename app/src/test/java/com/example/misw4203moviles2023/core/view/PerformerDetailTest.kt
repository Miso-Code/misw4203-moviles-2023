package com.example.misw4203moviles2023.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.mockPerformer
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.viewModel.PerformerDetailViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class PerformerDetailTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PerformerDetailViewModel

    @Before
    fun setup() {
        viewModel = mock(PerformerDetailViewModel::class.java)
    }

    @Test
    fun testPerformerDetailViewModel() {
        val performer = mockPerformer(
            1,
            "Performer Name",
            "Performer Description",
            "Performer.png",
        )

        `when`(viewModel.performerModel).thenReturn(MutableLiveData(performer))

        val performerDetailFragment = PerformerDetail(viewModel)

        // Use launchFragment to create the fragment in isolation

        launchFragment<PerformerDetail>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    performerDetailFragment
            },
            fragmentArgs = bundleOf("performerId" to 1),
        )

        // Verify that the view model onCreate() method was called with the correct performer ID
        assertEquals(performer, viewModel.performerModel.value)
    }
}
