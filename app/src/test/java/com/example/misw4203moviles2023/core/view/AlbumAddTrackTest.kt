import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.domain.album.model.Track
import com.example.misw4203moviles2023.ui.viewModel.AlbumAddTrackViewModel
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.view.AlbumAddTrack
import com.google.android.material.textfield.TextInputEditText
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class AlbumAddTrackTest {

    @Mock
    private lateinit var viewModel: AlbumAddTrackViewModel
    private lateinit var albumAddTrackFragment: AlbumAddTrack

    @Before
    fun setup() {
        viewModel = mock(AlbumAddTrackViewModel::class.java)
        albumAddTrackFragment = AlbumAddTrack(viewModel)
        `when`(viewModel.isLoading).thenReturn(MutableLiveData(false))
    }

    @Test
    fun addTrackButtonClicked_InvokesViewModelMethod() {
        launchFragment<AlbumAddTrack>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    albumAddTrackFragment
            },
            fragmentArgs = bundleOf("albumId" to 1),
        )

        val trackName = "Test Track"
        val trackDuration = "02:30"

        val trackNameInput =
            albumAddTrackFragment.view?.findViewById(R.id.track_name_edit_text) as TextInputEditText
        val trackDurationInput =
            albumAddTrackFragment.view?.findViewById(R.id.track_duration_edit_text) as TextInputEditText
        val addTrackButton =
            albumAddTrackFragment.view?.findViewById(R.id.track_create_button) as Button

        trackNameInput.setText(trackName)
        trackDurationInput.setText(trackDuration)

        addTrackButton.performClick()

        verify(viewModel).addTrack(
            Track(
                id = -1,
                name = trackName,
                duration = trackDuration,
            ),
        )
    }
}
