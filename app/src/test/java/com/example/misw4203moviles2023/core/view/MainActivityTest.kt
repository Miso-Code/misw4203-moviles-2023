package com.example.misw4203moviles2023.core.view

import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.misw4203moviles2023.R
import com.example.misw4203moviles2023.ui.view.MainActivity
import com.google.android.material.navigation.NavigationView
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var activity: MainActivity
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()
        navController = activity.findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        drawerLayout = activity.findViewById(R.id.drawer_layout)
        navView = activity.findViewById(R.id.nav_view)
    }

    @Test
    fun testNavigation() {
        navView.menu.performIdentifierAction(R.id.albumList, 0)
        assertEquals(navController.currentDestination?.id.toString(), R.id.albumList.toString())

        navView.menu.performIdentifierAction(R.id.performerList, 0)
        assertEquals(navController.currentDestination?.id.toString(), R.id.performerList.toString())

        navView.menu.performIdentifierAction(R.id.collectorList, 0)
        assertEquals(navController.currentDestination?.id.toString(), R.id.collectorList.toString())
    }

    @Test
    fun testActionBarTitle() {
        activity.onOptionsItemSelected(
            mock(MenuItem::class.java, Mockito.RETURNS_DEEP_STUBS).apply {
                `when`(itemId).thenReturn(R.id.albumList)
            },
        )
        assertEquals(activity.supportActionBar?.title, activity.getString(R.string.menu_album_list))

        activity.onOptionsItemSelected(
            mock(MenuItem::class.java, Mockito.RETURNS_DEEP_STUBS).apply {
                `when`(itemId).thenReturn(R.id.performerList)
            },
        )
        assertEquals(
            activity.supportActionBar?.title,
            activity.getString(R.string.menu_performer_list),
        )

        activity.onOptionsItemSelected(
            mock(MenuItem::class.java, Mockito.RETURNS_DEEP_STUBS).apply {
                `when`(itemId).thenReturn(R.id.collectorList)
            },
        )
        assertEquals(
            activity.supportActionBar?.title,
            activity.getString(R.string.menu_collector_list),
        )
    }

    @Test
    fun testNavigateUp() {
        val result = activity.onSupportNavigateUp()
        assert(result)
    }

    @After
    fun tearDown() {
        activity.finish()
    }
}
