package com.example.misw4203moviles2023.data

import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.network.AlbumService

class AlbumRepository(service: AlbumService? = null) {
    private val api = service ?: AlbumService()
    suspend fun getAllAlbums(): List<AlbumModel> {
        return api.getAlbums()
    }
    suspend fun getAlbumById(id: Int): AlbumModel {
        return api.getAlbumById(id)
    }
}
