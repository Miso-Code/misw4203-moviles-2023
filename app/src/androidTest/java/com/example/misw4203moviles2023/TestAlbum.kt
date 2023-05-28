package com.example.misw4203moviles2023

import android.app.Application
import android.view.Gravity
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.misw4203moviles2023.domain.album.DeleteAlbumById
import com.example.misw4203moviles2023.domain.album.DeleteTrackFromAlbumById
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.Track
import com.example.misw4203moviles2023.ui.view.MainActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Collections

@RunWith(AndroidJUnit4::class)
class TestAlbum {
    private var albumIdList: List<Int> = Collections.emptyList()
    private var trackIdList: List<Int> = Collections.emptyList()

    val deleteAlbumById = DeleteAlbumById(ApplicationProvider.getApplicationContext())
    val deleteTrackFromAlbumById = DeleteTrackFromAlbumById(ApplicationProvider.getApplicationContext())

    @get:Rule
    val activityTestRule = ActivityScenarioRule(
        MainActivity::class.java,
    )

    @After
    fun teardown() = runBlocking {
        if (albumIdList.isNotEmpty()) {
            for (albumId in albumIdList) {
                for (trackId in trackIdList) {
                    deleteTrackFromAlbumById(albumId, trackId)
                }
                deleteAlbumById(albumId)
            }
        }
    }

    @Test
    fun list_album_and_detail() {
        var randomAlbumIndex = -1
        var itemCount: Int

        val expectedViewTitle: String = ApplicationProvider.getApplicationContext<Application>()
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
                val row: View = recyclerView.layoutManager!!.getChildAt(i)!!

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

        if (randomAlbumIndex != -1) {
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

            itemCount = 0
            onView(withId(R.id.track_list_recycler_view)).check { view, _ ->
                val recyclerView = view as RecyclerView

                itemCount = recyclerView.adapter!!.itemCount
                for (i in 0 until itemCount) {
                    val row: View = view.getChildAt(i)

                    assertThat(row.isShown, `is`(true))

                    assertThat(row.findViewById<View>(R.id.track_image).isShown, `is`(true))
                    assertThat(row.findViewById<View>(R.id.track_name).isShown, `is`(true))
                    assertThat(row.findViewById<View>(R.id.track_duration).isShown, `is`(true))
                }
            }

            if (itemCount != 0) {
                onView(withId(R.id.albumTracks)).check(matches(isDisplayed()))
            } else {
                onView(withId(R.id.albumTracks)).check(matches(not(isDisplayed())))
            }
        }
    }

    @Test
    fun test_create_album_with_track_show_in_list_and_detail() {
        Thread.sleep(1000)
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open()) // Open Drawer

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.albumCreate))

        var expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_album_create)
        matchToolbarTitle(expectedViewTitle)

        onView(withId(R.id.album_name_edit_text)).check(matches(withText("")))
        onView(withId(R.id.album_release_date_edit_text)).check(matches(withText("")))
        onView(withId(R.id.album_description_edit_text)).check(matches(withText("")))
        onView(withId(R.id.album_genre_edit_text)).check(matches(withText("")))
        onView(withId(R.id.album_record_label_edit_text)).check(matches(withText("")))
        onView(withId(R.id.album_cover_edit_text)).check(matches(withText("")))

        onView(withId(R.id.album_create_button)).check(matches(not(isEnabled())))
        onView(withId(R.id.album_create_loading_spinner)).check(matches(not(isDisplayed())))

        // create album
        val album = Album(
            0,
            "Test Album" + System.currentTimeMillis().toString(),
            "https://i.pinimg.com/originals/d7/ee/59/d7ee59f9ea2cee3358e2645ebb894acc.jpg",
            "30/06/2017",
            "Test Description",
            "Rock",
            "EMI",
            emptyList(),
        )

        onView(withId(R.id.album_name_edit_text)).perform(typeText(album.name))

        onView(withId(R.id.album_release_date_edit_text)).perform(click())
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val date = dateFormat.parse(album.releaseDate)!!
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            @Suppress("DEPRECATION")
            PickerActions.setDate(
                date.year + 1900,
                date.month + 1,
                date.date,
            ),
        )

        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.album_description_edit_text)).perform(typeText(album.description))

        onView(withId(R.id.album_genre_input)).perform(scrollTo())

        onView(withId(R.id.album_genre_edit_text)).perform(typeText(album.genre))

        onData(allOf(`is`(instanceOf(String::class.java)), `is`(album.genre))).inRoot(
            isPlatformPopup(),
        ).perform(click())

        onView(withId(R.id.album_record_label_input)).perform(scrollTo())

        onView(withId(R.id.album_record_label_edit_text)).perform(typeText(album.recordLabel))

        onData(allOf(`is`(instanceOf(String::class.java)), `is`(album.recordLabel))).inRoot(
            isPlatformPopup(),
        ).perform(click())

        onView(withId(R.id.album_cover_preview)).perform(scrollTo())

        onView(withId(R.id.album_cover_preview)).check(matches(isDisplayed()))

        onView(withId(R.id.album_cover_preview)).check(matches(withImageDrawable(R.drawable.ic_album)))

        onView(withId(R.id.album_cover_edit_text)).perform(typeText(album.cover))
        onView(withId(R.id.album_cover_edit_text)).perform(closeSoftKeyboard())

        Thread.sleep(1000)

        onView(withId(R.id.album_cover_preview)).check(matches(isDisplayed()))

        onView(withId(R.id.album_cover_preview)).check(matches(not(withImageDrawable(R.drawable.ic_album))))

        onView(withId(R.id.album_create_button)).check(matches(isEnabled()))

        onView(withId(R.id.album_create_button)).perform(scrollTo())

        onView(withId(R.id.album_create_button)).perform(click())

        Thread.sleep(1000)
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
            .check(matches(DrawerMatchers.isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open()) // Open Drawer

        // Go to Album List
        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.albumList))

        // Check that album list fragment is visible
        expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_album_list)
        matchToolbarTitle(expectedViewTitle)

        var found = false
        var index = 0
        var view: RecyclerView? = null
        // Check that album is in list
        Thread.sleep(1000)
        onView(withId(R.id.album_list_recycler_view)).check { _view, _ ->
            val recyclerView = _view as RecyclerView

            val itemCount = recyclerView.adapter!!.itemCount

            assertThat(itemCount, greaterThan(0))

            // loop until album is found
            for (i in 0 until itemCount) {
                val row: View = recyclerView.layoutManager!!.getChildAt(i)!!

                assertThat(row.isShown, `is`(true))

                val albumName = row.findViewById<TextView>(R.id.album_name).text.toString()
                if (albumName == album.name) {
                    found = true
                    index = i
                    break
                }
            }
            assertThat(found, `is`(true))
            view = recyclerView
        }

        val row = view?.getChildAt(index)
        val albumReleaseDate = row?.findViewById<TextView>(R.id.album_release_date)?.text.toString()
        assertThat(albumReleaseDate, `is`(album.releaseDate))

        onView(withId(R.id.album_list_recycler_view)).perform(
            scrollToPosition<RecyclerView.ViewHolder>(index),
        )
        onView(withId(R.id.album_list_recycler_view)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(index, click()),
        )

        Thread.sleep(1000)

        matchToolbarTitle(album.name)

        onView(withId(R.id.albumDetailImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.albumDetailImageView)).check(matches(not(withImageDrawable(R.drawable.ic_album))))

        onView(withId(R.id.albumGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.albumGenre)).check(matches(withText("Género del álbum: ${album.genre}")))

        onView(withId(R.id.albumRecordLabel)).check(matches(isDisplayed()))
        onView(withId(R.id.albumRecordLabel)).check(matches(withText(album.recordLabel)))

        onView(withId(R.id.albumDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.albumDescription)).check(matches(withText(album.description)))

        onView(withId(R.id.albumId)).check(matches(not(isDisplayed())))
        // get current text for R.id.albuId
        onView(withId(R.id.albumId)).check { _view, _ ->
            if (_view is TextView) {
                val albumId = _view.text.toString().toInt()
                albumIdList = albumIdList + albumId
            }
        }

        // click on the add track button
        onView(withId(R.id.addTrackButton)).perform(click())

        Thread.sleep(1000)
        expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_add_track)
        matchToolbarTitle(expectedViewTitle)

        onView(withId(R.id.track_name_edit_text)).check(matches(withText("")))
        onView(withId(R.id.track_duration_edit_text)).check(matches(withText("")))

        onView(withId(R.id.track_create_button)).check(matches(not(isEnabled())))
        onView(withId(R.id.track_create_loading_spinner)).check(matches(not(isDisplayed())))

        val track = Track(
            id = -1,
            name = "Track 1",
            duration = "03:00",
        )

        onView(withId(R.id.track_name_edit_text)).perform(typeText(track.name))

        onView(withId(R.id.track_duration_edit_text)).perform(click())

        val time = track.duration.split(":")
        val minutes = time[0].toInt()
        val seconds = time[1].toInt()
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name))).perform(
            PickerActions.setTime(
                minutes,
                seconds,
            ),
        )

        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.track_duration_edit_text)).perform(closeSoftKeyboard())

        onView(withId(R.id.track_create_button)).check(matches(isEnabled()))

        onView(withId(R.id.track_create_button)).perform(scrollTo())

        onView(withId(R.id.track_create_button)).perform(click())

        onView(withContentDescription("Navigate up")).perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.albumDetailImageView)).check(matches(isDisplayed()))
        onView(withId(R.id.albumGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.albumRecordLabel)).check(matches(isDisplayed()))
        onView(withId(R.id.albumDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.albumTracks)).check(matches(isDisplayed()))

        onView(withId(R.id.track_list_recycler_view)).check { _view, _ ->
            val recyclerView = _view as RecyclerView

            assertThat(recyclerView.adapter!!.itemCount, `is`(1))

            val trackRow: View = recyclerView.getChildAt(0)

            assertThat(trackRow.isShown, `is`(true))

            assertThat(trackRow.findViewById<View>(R.id.track_image).isShown, `is`(true))

            assertThat(trackRow.findViewById<View>(R.id.track_name).isShown, `is`(true))
            assertThat(trackRow.findViewById<TextView>(R.id.track_name).text.toString(), `is`(track.name))

            assertThat(trackRow.findViewById<View>(R.id.track_duration).isShown, `is`(true))
            assertThat(trackRow.findViewById<TextView>(R.id.track_duration).text.toString(), `is`(track.duration))

            val trackId = trackRow.findViewById<TextView>(R.id.trackId).text.toString().toInt()
            trackIdList = trackIdList + trackId
        }
    }
}
