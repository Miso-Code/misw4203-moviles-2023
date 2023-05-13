package com.example.misw4203moviles2023.ui.view

import com.example.misw4203moviles2023.R
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumListDirectionsTest {
    @Test
    fun testActionAlbumListToAlbumDetail() {
        val albumId = 123
        val navDirections = AlbumListDirections.actionAlbumListToAlbumDetail(albumId)
        assertEquals(navDirections.actionId, R.id.action_albumList_to_albumDetail)
    }
}
