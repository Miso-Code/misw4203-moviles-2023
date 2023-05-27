package com.example.misw4203moviles2023.domain.collector

import android.content.Context
import com.example.misw4203moviles2023.data.CollectorRepository
import com.example.misw4203moviles2023.domain.collector.model.Collector

class GetCollectorById(context: Context) {
    private val repository = CollectorRepository(context)

    suspend operator fun invoke(id: Int): Collector {
        val collector: Collector? = repository.getCollectorByIdFromApi(id)

        return if (collector !== null) {
            repository.insertCollector(collector)
            collector
        } else {
            val response = repository.getCollectorByIdFromDb(id)
            response
        }
    }
}
