package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.AlbumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getAlbums(): List<AlbumModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(AlbumApiClient::class.java).getAllAlbums()
            response.body() ?: emptyList()
        }
    }
}
