package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.AlbumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class AlbumService(apiClient: AlbumApiClient? = null) {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val defaultApiClient = retrofit.create(AlbumApiClient::class.java)
    private val apiClient = apiClient ?: defaultApiClient

    suspend fun getAlbums(): List<AlbumModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getAllAlbums()
                response.body() ?: emptyList()
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                emptyList()
            }
        }
    }


    suspend fun getAlbumById(id: Int): AlbumModel? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getAlbumById(id)
                response.body() ?: AlbumModel(0, "", "", "", "", "", "", emptyList())
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                null
            }
        }
    }
}
