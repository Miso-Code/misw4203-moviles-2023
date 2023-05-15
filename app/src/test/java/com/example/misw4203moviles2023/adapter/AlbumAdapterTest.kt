package com.example.misw4203moviles2023.adapter

import android.content.Context
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.example.misw4203moviles2023.mockAlbum
import com.example.misw4203moviles2023.test.TestApplication
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestApplication::class)
@RunWith(RobolectricTestRunner::class)
class AlbumAdapterTest {

    private lateinit var adapter: AlbumAdapter
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val albumList = listOf(
            mockAlbum(
                1,
                "Album 1",
                "https://example.com/album1.jpg",
                "2023-01-01",
                "Description 1",
                "Rock",
                "Record Label 1",
            ),
            mockAlbum(
                2,
                "Album 2",
                "https://example.com/album2.jpg",
                "2023-02-01",
                "Description 2",
                "Pop",
                "Record Label 2",
            ),
        )
        adapter = AlbumAdapter(context, albumList)
    }

    @Test
    fun onCreateViewHolder_inflatesAlbumRowBinding() {
        val parent = FrameLayout(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        assertThat(viewHolder, instanceOf(AlbumAdapter.AlbumViewHolder::class.java))
    }

    @Test
    fun onBindViewHolder_bindsAlbum() {
        val parent = FrameLayout(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        adapter.onBindViewHolder(viewHolder, 0)

        assertEquals(viewHolder.itemView.isClickable, true)
        assertEquals(viewHolder.itemView.hasOnClickListeners(), true)
    }

    @Test
    fun getItemCount_returnsAlbumListSize() {
        assertEquals(adapter.itemCount, 2)
    }
}
