package com.example.misw4203moviles2023.data

import android.content.Context
import com.example.misw4203moviles2023.data.database.DataBaseService
import com.example.misw4203moviles2023.data.database.entities.PerformerAlbumCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.data.database.entities.toWithAlbumsDB
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.data.network.AlbumService
import com.example.misw4203moviles2023.data.network.PerformerService
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.performer.model.Performer
import com.example.misw4203moviles2023.domain.performer.model.toDomain

class PerformerRepository(service: PerformerService? = null, context: Context) {
	private val api = service ?: PerformerService()
	private val dao = DataBaseService(context)

	suspend fun getPerformersFromApi(): List<Performer> {
		val response: List<PerformerModel> = api.getPerformers()
		return response.map { it.toDomain() }
	}

	suspend fun getPerformersFromDB(): List<Performer> {
		val response = dao.getAllPerformerDao()
		response.map { it.toDomain() }
		return response.map { it.toDomain() }
	}

	suspend fun getPerformerByIdFromApi(id: Int): Performer {
		val response = api.getPerformerById(id)
		return response.toDomain()
	}

	suspend fun clearAllPerformer() {
		dao.deleteAllPerformerDao()
	}

	suspend fun insertAllPerformer(performers: List<Performer>) {
		val dataPerformers = performers.map { it.toWithAlbumsDB() }
		val data = performers.map{ PerformerAlbumCrossRefEntity() }
		dao.insertPerformerWithAlbumsDao()
	}
}
