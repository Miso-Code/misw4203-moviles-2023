package com.example.misw4203_moviles_2023.data.network

import com.example.misw4203_moviles_2023.data.model.AlbumModel
import retrofit2.Response
import retrofit2.http.GET

interface AlbumApiClient {
    @GET("/albums")
    suspend fun getAllAlbums(): Response<List<AlbumModel>>
}