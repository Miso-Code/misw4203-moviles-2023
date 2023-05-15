package com.example.misw4203moviles2023.data

import android.content.Context
import android.util.Log
import com.example.misw4203moviles2023.data.database.DataBaseService
import com.example.misw4203moviles2023.data.database.entities.AlbumEntity
import com.example.misw4203moviles2023.data.database.entities.TrackEntity
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.network.AlbumService
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.toDomain

class AlbumRepository(service: AlbumService? = null, context: Context) {
    private val api = service ?: AlbumService()
    private val dao = DataBaseService(context)
    suspend fun getAllAlbumsFromApi(): List<Album> {
        val response: List<AlbumModel> = api.getAlbums()
        return response.map { it.toDomain() }
    }

    suspend fun getAllAlbumsFromDB(): List<Album> {
        val response = dao.getAllAlbumsDao()
        return response.map { it.toDomain() }
    }

    suspend fun insertAlbums(albums: List<AlbumEntity>, tracks: List<List<TrackEntity>>) {
        dao.insertAlbumsDao(albums)
        tracks.forEach { dao.insertTracksDao(it) }
    }

    suspend fun clearAlbums() {
        dao.deleteAlbumsDao()
    }

    suspend fun getAlbumByIdFromApi(id: Int): Album? {
        val response = api.getAlbumById(id)
        if (response !== null) {
            response.tracks.map { Log.d("tracks", it.name) }
            return response.toDomain()
        }
        return null
    }

    suspend fun getAlbumByIdFromDb(id: Int): Album? {
        val response = dao.getAlbumByIdDao(id)
        return response.toDomain()
    }
}
