package com.example.misw4203moviles2023.data.network

import android.util.Log
import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.CollectorModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class CollectorService(apiClient: CollectorApiClient? = null) {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val defaultApiClient = retrofit.create(CollectorApiClient::class.java)
    private val apiClient = apiClient ?: defaultApiClient
    suspend fun getCollectors(): List<CollectorModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getCollectors()
                response.body() ?: emptyList()
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                emptyList()
            }
        }
    }

    suspend fun getCollectorsById(id: Int): CollectorModel? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getCollectorById(id)
                val collector = response.body() ?: CollectorModel(0, "", "", "")

                val albumsResponse = apiClient.getCollectorAlbumsById(id)
                val performerResponse = apiClient.getCollectorPerformersById(id)

                collector.albums = albumsResponse.body()?.map { it.album }
                collector.performers = performerResponse.body()
                Log.d("getCollectorsById", collector.toString())
                collector
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                null
            }
        }
    }
}
