package com.example.misw4203moviles2023.data

import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.data.network.CollectorService

class CollectorRepository {
    private val api = CollectorService()
    suspend fun getAllCollectors(): List<CollectorModel> {
        return api.getCollectors()
    }
    suspend fun getCollectorById(id: Int): CollectorModel {
        return api.getCollectorsById(id)
    }
}
