import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.view.AlbumCreate
import com.example.misw4203moviles2023.ui.viewModel.AlbumCreateViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AlbumCreateTest {

    @Mock
    private lateinit var viewModel: AlbumCreateViewModel
    private lateinit var albumCreateFragment: AlbumCreate

    @Before
    fun setup() {
        viewModel = mock(AlbumCreateViewModel::class.java)
        albumCreateFragment = AlbumCreate(viewModel)
    }

    @Test
    fun create_album_lists_genres_and_recordLabels() {
        val genres = listOf("Genre1", "Genre2", "Genre3")
        val recordLabels = listOf("RecordLabel1", "RecordLabel2", "RecordLabel3")

        `when`(viewModel.genres).thenReturn(MutableLiveData(genres))
        `when`(viewModel.recordLabels).thenReturn(MutableLiveData(recordLabels))
        `when`(viewModel.isLoading).thenReturn(MutableLiveData(false))

        launchFragment<AlbumCreate>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    albumCreateFragment
            },
        )
        assertEquals(genres, viewModel.genres.value)
        assertEquals(recordLabels, viewModel.recordLabels.value)
    }
}
