package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.AlbumModel
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApiClient {
    @GET("/albums")
    suspend fun getAllAlbums(): Response<List<AlbumModel>>
}
