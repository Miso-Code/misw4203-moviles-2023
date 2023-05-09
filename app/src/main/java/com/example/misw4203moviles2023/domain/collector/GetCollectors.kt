package com.example.misw4203moviles2023.domain.collector

import com.example.misw4203moviles2023.data.CollectorRepository
import com.example.misw4203moviles2023.data.model.CollectorModel

class GetCollectors {
    private val repository = CollectorRepository()

    suspend operator fun invoke(): List<CollectorModel> = repository.getAllCollectors()
}
