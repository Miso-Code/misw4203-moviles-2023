package com.example.misw4203moviles2023.data

import android.content.Context
import com.example.misw4203moviles2023.data.database.DataBaseService
import com.example.misw4203moviles2023.data.database.entities.PerformerAlbumCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.data.database.entities.toWithAlbumsDB
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.data.network.PerformerService
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
        return response.map { it.toDomain() }
    }

    suspend fun getPerformerByIdFromApi(id: Int): Performer? {
        val response = api.getPerformerById(id)
        if (response != null) {
            return response.toDomain()
        }
        return null
    }

    suspend fun clearAllPerformer() {
        dao.deleteAllPerformerDao()
    }

    suspend fun clearAllPerformerWithAlbum() {
        dao.deleteAlbumsDao()
        dao.deleteAllPerformerDao()
    }

    suspend fun insertAllPerformer(performers: List<Performer>) {
        performers.map { it.toWithAlbumsDB() }
        val dataDb = performers.map { it.toDatabase() }
        performers.map {
            it.albums.map { album ->
                dao.insertPerformerWithAlbumDao(
                    PerformerAlbumCrossRefEntity(albumId = album.id, performerId = it.id),
                )
            }
        }
        dao.insertPerformerDao(dataDb)
    }
}
