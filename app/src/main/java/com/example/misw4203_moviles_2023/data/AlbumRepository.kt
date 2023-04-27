package com.example.misw4203_moviles_2023.data

import com.example.misw4203_moviles_2023.data.model.AlbumModel
import com.example.misw4203_moviles_2023.data.network.AlbumService

class AlbumRepository {
    private val api = AlbumService()
    suspend fun getAllAlbums(): List<AlbumModel> {
        val response: List<AlbumModel> = api.getAlbums()
        return response
    }
}
