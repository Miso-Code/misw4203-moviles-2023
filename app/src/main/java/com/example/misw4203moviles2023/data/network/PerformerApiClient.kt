package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.data.model.PerformerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PerformerApiClient {
    @GET("/musicians")
    suspend fun getPerformers(): Response<List<PerformerModel>>

    @GET("/musicians/{id}")
    suspend fun getPerformerById(@Path("id") id: Int): Response<PerformerModel>
}
