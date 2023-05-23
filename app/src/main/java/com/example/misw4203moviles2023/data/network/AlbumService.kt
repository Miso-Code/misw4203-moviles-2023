package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.AlbumModelCreate
import com.example.misw4203moviles2023.data.model.AlbumModelNoTracks
import com.example.misw4203moviles2023.data.model.TrackModel
import com.example.misw4203moviles2023.data.model.TrackModelCreate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class AlbumService(apiClient: AlbumApiClient? = null) {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val defaultApiClient = retrofit.create(AlbumApiClient::class.java)
    private val apiClient = apiClient ?: defaultApiClient

    suspend fun getAlbums(): List<AlbumModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getAllAlbums()
                response.body() ?: emptyList()
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                emptyList()
            }
        }
    }

    suspend fun getAlbumById(id: Int): AlbumModel? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getAlbumById(id)
                response.body() ?: AlbumModel(0, "", "", "", "", "", "", emptyList())
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                null
            }
        }
    }

    suspend fun createAlbum(album: AlbumModelCreate): AlbumModelNoTracks? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.createAlbum(album)
                response.body() ?: AlbumModelNoTracks(0, "", "", "", "", "", "")
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                null
            }
        }
    }

    suspend fun deleteAlbumById(id: Int): Unit? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.deleteAlbumById(id)
                response.body() ?: Unit
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                null
            }
        }
    }

    suspend fun addTrackToAlbum(albumId: Int, track: TrackModelCreate): TrackModel? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.addTrackToAlbum(albumId, track)
                response.body() ?: TrackModel(0, "", "")
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                null
            }
        }
    }

    suspend fun deleteTrackFromAlbum(albumId: Int, trackId: Int): Unit? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.deleteTrackFromAlbum(albumId, trackId)
                response.body() ?: Unit
            } catch (e: IOException) {
                println("Error: ${e.message} : ${e.stackTrace}")
                null
            }
        }
    }
}
