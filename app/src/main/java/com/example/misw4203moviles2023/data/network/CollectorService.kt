package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.CollectorModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CollectorService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getCollectors(): List<CollectorModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(CollectorApiClient::class.java).getCollectors()
            response.body() ?: emptyList()
        }
    }

    suspend fun getCollectorsById(id: Int): CollectorModel {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(CollectorApiClient::class.java).getCollectorById(id)
            response.body() ?: CollectorModel(0, "", "", "")
        }
    }
}
