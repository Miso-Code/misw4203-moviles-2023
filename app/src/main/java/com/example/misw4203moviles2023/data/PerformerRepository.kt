package com.example.misw4203moviles2023.data

import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.data.network.PerformerService

class PerformerRepository {
    private val api = PerformerService()
    suspend fun getPerformers(): List<PerformerModel> {
        return api.getPerformers()
    }
    suspend fun getPerformerById(id: Int): PerformerModel {
        return api.getPerformerById(id)
    }
}
