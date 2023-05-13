package com.example.misw4203moviles2023

import android.app.Application
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.misw4203moviles2023.ui.view.MainActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Collections

@RunWith(AndroidJUnit4::class)
class TestAlbum {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(
        MainActivity::class.java,
    )

    @Test
    fun list_album_and_detail() {
        var randomAlbumIndex = 0
        var itemCount = 0

        var expectedViewTitle: String = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_album_list)
        matchToolbarTitle(expectedViewTitle)

        // onView(withId(R.id.progress_bar)).check(matches(isDisplayed())) // sometimes the bar is not visible because the list is loaded too fast
        Thread.sleep(1000)
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.album_list_recycler_view)).check(matches(isDisplayed()))

        val elementTexts = ArrayList<String>()
        onView(withId(R.id.album_list_recycler_view)).check { view, _ ->
            val recyclerView = view as RecyclerView

            itemCount = recyclerView.adapter!!.itemCount

            for (i in 0 until itemCount) {
                val row: View = view.getChildAt(i)

                assertThat(row.isClickable, `is`(true))
                assertThat(row.isShown, `is`(true))

                assertThat(row.findViewById<View>(R.id.album_name).isShown, `is`(true))
                assertThat(row.findViewById<View>(R.id.album_image).isShown, `is`(true))
                assertThat(row.findViewById<View>(R.id.album_release_date).isShown, `is`(true))

                val textView = row.findViewById<View>(R.id.album_name) as TextView
                elementTexts.add(textView.text.toString())
            }
            val sortedList: List<String> = ArrayList(elementTexts)
            Collections.sort(sortedList, Collections.reverseOrder())

            assertThat(elementTexts, `is`(sortedList))

            randomAlbumIndex = (0 until itemCount).random()
        }

        if (randomAlbumIndex != 0) {
            onView(withId(R.id.album_list_recycler_view)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(randomAlbumIndex),
            )
            onView(withId(R.id.album_list_recycler_view)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(randomAlbumIndex, click()),
            )

            Thread.sleep(1000)

            matchToolbarTitle(elementTexts[randomAlbumIndex])

            onView(withId(R.id.albumDetailImageView)).check(matches(isDisplayed()))
            onView(withId(R.id.albumGenre)).check(matches(isDisplayed()))
            onView(withId(R.id.albumRecordLabel)).check(matches(isDisplayed()))

            onView(withId(R.id.track_list_recycler_view)).check { view, _ ->
                val recyclerView = view as RecyclerView

                val itemCount = recyclerView.adapter!!.itemCount

                if (itemCount != 0) {
                    onView(withId(R.id.albumTracks)).check(matches(isDisplayed()))
                }

                for (i in 0 until itemCount) {
                    val row: View = view.getChildAt(i)

                    assertThat(row.isShown, `is`(true))

                    assertThat(row.findViewById<View>(R.id.track_image).isShown, `is`(true))
                    assertThat(row.findViewById<View>(R.id.track_name).isShown, `is`(true))
                    assertThat(row.findViewById<View>(R.id.track_duration).isShown, `is`(true))
                }
            }
        }
    }
}
