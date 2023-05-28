package com.example.misw4203moviles2023.domain.collector

import android.content.Context
import com.example.misw4203moviles2023.data.CollectorRepository
import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.domain.collector.model.Collector
import com.example.misw4203moviles2023.domain.collector.model.toDomain

class GetCollectors(context: Context) {
    private val repository = CollectorRepository(context)

    suspend operator fun invoke(): List<Collector> {
        val collectors: List<CollectorModel> = repository.getAllCollectorsFromApi()

        return if (collectors.isNotEmpty()) {
            repository.clearCollectors()
            repository.insertCollectors(collectors.map { it.toDomain() })
            return collectors.map { it.toDomain() }
        } else {
            repository.getAllCollectorsFromDB()
        }
    }
}
