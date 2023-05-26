package com.example.misw4203moviles2023.adapter

import android.content.Context
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.example.misw4203moviles2023.mockCollector
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
class CollectorAdapterTest {

    private lateinit var adapter: CollectorAdapter
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val collectorList = listOf(
            mockCollector(
                1,
                "Collector 1",
                "330898",
                "2023-01-01",
            ),
            mockCollector(
                2,
                "Collector 2",
                "3308985",
                "2023-02-01",
            ),
        )
        adapter = CollectorAdapter(collectorList)
    }

    @Test
    fun onCreateViewHolder_inflatesCollectorRowBinding() {
        val parent = FrameLayout(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        assertThat(viewHolder, instanceOf(CollectorAdapter.CollectorViewHolder::class.java))
    }

    @Test
    fun onBindViewHolder_bindsCollector() {
        val parent = FrameLayout(context)
        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        adapter.onBindViewHolder(viewHolder, 0)

        assertEquals(viewHolder.itemView.isClickable, true)
        assertEquals(viewHolder.itemView.hasOnClickListeners(), true)
    }

    @Test
    fun getItemCount_returnsCollectorListSize() {
        assertEquals(adapter.itemCount, 2)
    }
}
