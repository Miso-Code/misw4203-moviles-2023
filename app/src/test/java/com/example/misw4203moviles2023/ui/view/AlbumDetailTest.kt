import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.view.AlbumDetail
import com.example.misw4203moviles2023.ui.viewModel.AlbumDetailViewModel
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
class AlbumDetailTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AlbumDetailViewModel

    @Before
    fun setup() {
        viewModel = mock(AlbumDetailViewModel::class.java)
    }

    @Test
    fun testAlbumDetailViewModel() {
        val albumModel = mockAlbumModel(
            1,
            "Album Name",
            "Album Description",
            "Album Genre",
            "Album Record Label",
            "Album Cover",
            "Album recordLabel",
        )

        `when`(viewModel.albumModel).thenReturn(MutableLiveData(albumModel))

        val albumDetailFragment = AlbumDetail(viewModel)

        // Use launchFragment to create the fragment in isolation

        launchFragment<AlbumDetail>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    albumDetailFragment
            },
            fragmentArgs = bundleOf("albumId" to 1),
        )

        // Verify that the view model onCreate() method was called with the correct album ID
        assertEquals(albumModel, viewModel.albumModel.value)
    }
}
