package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.CollectorModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorApiClient {
    @GET("/collectors")
    suspend fun getCollectors(): Response<List<CollectorModel>>

    @GET("/collectors/{id}")
    suspend fun getCollectorById(@Path("id") id: Int): Response<CollectorModel>
}
