package com.example.misw4203moviles2023.data

import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.network.AlbumService

class AlbumRepository {
    private val api = AlbumService()
    suspend fun getAllAlbums(): List<AlbumModel> {
        val response: List<AlbumModel> = api.getAlbums()
        return response
    }
}
