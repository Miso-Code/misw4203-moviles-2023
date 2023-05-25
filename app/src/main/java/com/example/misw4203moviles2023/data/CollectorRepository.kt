package com.example.misw4203moviles2023.data

import android.content.Context
import com.example.misw4203moviles2023.data.database.DataBaseService
import com.example.misw4203moviles2023.data.database.entities.CollectorEntity
import com.example.misw4203moviles2023.data.database.entities.toDataBase
import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.data.network.CollectorService
import com.example.misw4203moviles2023.domain.collector.model.Collector
import com.example.misw4203moviles2023.domain.collector.model.toDomain

class CollectorRepository(service: CollectorService? = null, context: Context) {
	private val api = service ?: CollectorService()
	private val dao = DataBaseService(context)

	suspend fun getAllCollectorsFromApi(): List<CollectorModel> {
		return api.getCollectors()
	}

	suspend fun getAllCollectorsFromDB(): List<Collector> {
		val response = dao.getAllCollectorDao()
		return response.map { it.toDomain() }
	}

	suspend fun insertCollectors(collectors: List<Collector>) {
		val data: List<CollectorEntity> = collectors.map { it.toDataBase() }
		dao.insertCollectorDao(data)
	}

	suspend fun clearCollectors() {
		dao.deleteAllCollectorDao()
	}
}
