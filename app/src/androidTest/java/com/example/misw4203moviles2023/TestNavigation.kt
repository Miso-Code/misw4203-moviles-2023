package com.example.misw4203moviles2023

import android.app.Application
import android.view.Gravity
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.misw4203moviles2023.ui.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestNavigation {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(
        MainActivity::class.java,
    )

    @Test
    fun navigate_between_menu_items() {
        // Check that you Activity was opened and album list fragment is visible
        var expectedViewTitle: String = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_album_list)
        matchToolbarTitle(expectedViewTitle)

        Thread.sleep(1000)
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open()); // Open Drawer

        // Go to Artist List
        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.performerList))

        // Check that artist list fragment is visible
        expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_performer_list)
        matchToolbarTitle(expectedViewTitle)

        Thread.sleep(1000)
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open()); // Open Drawer

        // Go to Collector List
        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.collectorList))

        // Check that collector list fragment is visible
        expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_collector_list)
        matchToolbarTitle(expectedViewTitle)

        Thread.sleep(1000)
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open()); // Open Drawer

        // Go to Create Album
        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.albumCreate))

        // Check that album create fragment is visible
        expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_album_create)
        matchToolbarTitle(expectedViewTitle)

        Thread.sleep(1000)
        // Open Drawer to click on navigation.
        onView(withId(R.id.drawer_layout))
            .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
            .perform(DrawerActions.open()); // Open Drawer

        // Go to Album List
        onView(withId(R.id.nav_view))
            .perform(NavigationViewActions.navigateTo(R.id.albumList))

        // Check that album list fragment is visible
        expectedViewTitle = ApplicationProvider.getApplicationContext<Application>()
            .getString(R.string.menu_album_list)
        matchToolbarTitle(expectedViewTitle)
    }
}
