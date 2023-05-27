package com.example.misw4203moviles2023.adapter

import android.content.Context
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.example.misw4203moviles2023.mockPerformer
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
class PerformerAdapterTest {

    private lateinit var adapter: PerformerAdapter
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val performerList = listOf(
            mockPerformer(
                1,
                "Performer 1",
                "Performer description",
                "https://example.com/performer1.jpg",
            ),
            mockPerformer(
                2,
                "Performer 2",
                "Performer description",
                "https://example.com/performer2.jpg",
            ),
        )
        adapter = PerformerAdapter(context, performerList)
    }

    @Test
    fun onCreateViewHolder_inflatesPerformerRowBinding() {
        val parent = FrameLayout(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        assertThat(viewHolder, instanceOf(PerformerAdapter.PerformerViewHolder::class.java))
    }

    @Test
    fun onBindViewHolder_bindsPerformer() {
        val parent = FrameLayout(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        adapter.onBindViewHolder(viewHolder, 0)

        assertEquals(viewHolder.itemView.isClickable, true)
        assertEquals(viewHolder.itemView.hasOnClickListeners(), true)
    }

    @Test
    fun getItemCount_returnsPerformerListSize() {
        assertEquals(adapter.itemCount, 2)
    }
}
