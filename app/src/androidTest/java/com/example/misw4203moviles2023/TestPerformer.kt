package com.example.misw4203moviles2023

import android.app.Application
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.misw4203moviles2023.ui.view.MainActivity
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Collections

@RunWith(AndroidJUnit4::class)
class TestPerformer {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(
        MainActivity::class.java,
    )

    @Test
    fun list_performer_and_detail() {
        var randomAlbumIndex = -1
        var itemCount: Int

        var expectedViewTitle: String = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_album_list)

        matchToolbarTitle(expectedViewTitle)

        Thread.sleep(1000)

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(open()) // Open Drawer

        // Go to Collector List
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.performerList))

        // Check that collector list fragment is visible
        expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_performer_list)
        matchToolbarTitle(expectedViewTitle)

        Thread.sleep(1000)

        onView(withId(R.id.performer_progress_bar)).check(matches(not(isDisplayed())))

        onView(withId(R.id.performer_list_recycler_view)).check(matches(isDisplayed()))

        val elementTexts = ArrayList<String>()
        onView(withId(R.id.performer_list_recycler_view)).check { view, _ ->
            val recyclerView = view as RecyclerView

            itemCount = recyclerView.adapter!!.itemCount

            for (i in 0 until itemCount) {
                val row: View = view.getChildAt(i)

                assertThat(row.isClickable, CoreMatchers.`is`(true))
                assertThat(row.isShown, CoreMatchers.`is`(true))

                assertThat(
                    row.findViewById<View>(R.id.performer_name).isShown,
                    CoreMatchers.`is`(true),
                )
                assertThat(
                    row.findViewById<View>(R.id.performer_image).isShown,
                    CoreMatchers.`is`(true),
                )

                val textView = row.findViewById<View>(R.id.performer_name) as TextView
                elementTexts.add(textView.text.toString())
            }
            val sortedList: List<String> = ArrayList(elementTexts)
            Collections.sort(sortedList, Collections.reverseOrder())

            assertThat(elementTexts, CoreMatchers.`is`(sortedList))

            randomAlbumIndex = (0 until itemCount).random()
        }

        if (randomAlbumIndex != -1) {
            onView(withId(R.id.performer_list_recycler_view)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(randomAlbumIndex),
            )
            onView(withId(R.id.performer_list_recycler_view)).perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    randomAlbumIndex,
                    click(),
                ),
            )

            Thread.sleep(1000)

            matchToolbarTitle(elementTexts[randomAlbumIndex])

            onView(withId(R.id.performerDetailImageView)).check(
                matches(
                    isDisplayed(),
                ),
            )
            onView(withId(R.id.performerDescription)).check(matches(isDisplayed()))

            itemCount = 0
            onView(withId(R.id.performer_list_recycler_view)).check { view, _ ->
                val recyclerView = view as RecyclerView

                itemCount = recyclerView.adapter!!.itemCount
                Thread.sleep(1000)

                for (i in 0 until itemCount) {
                    val row: View = view.getChildAt(i)

                    assertThat(row.isShown, CoreMatchers.`is`(true))

                    assertThat(
                        row.findViewById<View>(R.id.album_image).isShown,
                        CoreMatchers.`is`(true),
                    )
                    assertThat(
                        row.findViewById<View>(R.id.album_name).isShown,
                        CoreMatchers.`is`(true),
                    )
                    assertThat(
                        row.findViewById<View>(R.id.album_release_date).isShown,
                        CoreMatchers.`is`(true),
                    )
                }
            }
            if (itemCount != 0) {
                onView(withId(R.id.performerAlbums)).check(matches(isDisplayed()))
            } else {
                onView(withId(R.id.performerAlbums)).check(matches(not(isDisplayed())))
            }
        }
    }
}
