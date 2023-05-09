package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.PerformerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PerformerService {
    private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getPerformers(): List<PerformerModel> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(PerformerApiClient::class.java).getPerformers()
            response.body() ?: emptyList()
        }
    }

    suspend fun getPerformerById(id: Int): PerformerModel {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(PerformerApiClient::class.java).getPerformerById(id)
            response.body() ?: PerformerModel(0, "", "", "", emptyList())
        }
    }
}
