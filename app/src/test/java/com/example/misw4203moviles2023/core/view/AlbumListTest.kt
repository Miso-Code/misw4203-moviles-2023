package com.example.misw4203moviles2023.core.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import com.example.misw4203moviles2023.mockAlbum
import com.example.misw4203moviles2023.test.TestApplication
import com.example.misw4203moviles2023.ui.view.AlbumList
import com.example.misw4203moviles2023.ui.viewModel.AlbumListViewModel
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
class AlbumListTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: AlbumListViewModel

    @Before
    fun setup() {
        viewModel = Mockito.mock(AlbumListViewModel::class.java)
    }

    @Test
    fun testAlbumListViewModel() {
        val albumList = listOf(
            mockAlbum(
                1,
                "Album Name",
                "Album Description",
                "Album Genre",
                "Album Record Label",
                "Album Cover",
                "Album recordLabel",
            ),
            mockAlbum(
                2,
                "Album Name",
                "Album Description",
                "Album Genre",
                "Album Record Label",
                "Album Cover",
                "Album recordLabel",
            ),
        )

        Mockito.`when`(viewModel.albumModel).thenReturn(MutableLiveData(albumList))

        val albumListFragment = AlbumList(viewModel)

        // Use launchFragment to create the fragment in isolation

        launchFragment<AlbumList>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String) =
                    albumListFragment
            },
        )

        // Verify that the view model onCreate() method was called with the correct album ID
        assertEquals(albumList, viewModel.albumModel.value)
    }
}
