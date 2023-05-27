import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.mockCollector
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.view.CollectorDetail
import com.example.misw4203moviles2023.ui.viewModel.CollectorDetailViewModel
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
class CollectorDetailTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CollectorDetailViewModel

    @Before
    fun setup() {
        viewModel = mock(CollectorDetailViewModel::class.java)
    }

    @Test
    fun testCollectorDetailViewModel() {
        val collectorModel = mockCollector(
            1,
            "Collector Name",
            "236558965",
            "collector@test.com",
        )

        `when`(viewModel.collectorModel).thenReturn(MutableLiveData(collectorModel))

        val collectorDetailFragment = CollectorDetail(viewModel)

        // Use launchFragment to create the fragment in isolation

        launchFragment<CollectorDetail>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    collectorDetailFragment
            },
            fragmentArgs = bundleOf("collectorId" to 1),
        )

        // Verify that the view model onCreate() method was called with the correct collector ID
        assertEquals(collectorModel, viewModel.collectorModel.value)
    }
}
