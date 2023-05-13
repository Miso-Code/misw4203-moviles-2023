package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.AlbumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumService(apiClient: AlbumApiClient? = null) {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val defaultApiClient = retrofit.create(AlbumApiClient::class.java)
    private val apiClient = apiClient ?: defaultApiClient

    suspend fun getAlbums(): List<AlbumModel> {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getAllAlbums()
            response.body() ?: emptyList()
        }
    }

    suspend fun getAlbumById(id: Int): AlbumModel {
        return withContext(Dispatchers.IO) {
            val response = apiClient.getAlbumById(id)
            response.body() ?: AlbumModel(0, "", "", "", "", "", "", emptyList())
        }
    }
}
