package com.example.misw4203moviles2023.data

import android.content.Context
import android.util.Log
import com.example.misw4203moviles2023.data.database.DataBaseService
import com.example.misw4203moviles2023.data.database.entities.AlbumEntity
import com.example.misw4203moviles2023.data.database.entities.TrackEntity
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.AlbumModelCreate
import com.example.misw4203moviles2023.data.model.TrackModelCreate
import com.example.misw4203moviles2023.data.network.AlbumService
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.Track
import com.example.misw4203moviles2023.domain.album.model.toDomain

@Suppress("TooManyFunctions")
class AlbumRepository(
    context: Context,
    service: AlbumService? = null,
    dbService: DataBaseService? = null,
) {
    private val api = service ?: AlbumService()
    private val dao = dbService ?: DataBaseService(context)
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

    suspend fun getAlbumByIdFromDb(id: Int): Album {
        val response = dao.getAlbumByIdDao(id)
        return response.toDomain()
    }

    suspend fun createAlbumToToApi(album: Album): Album? {
        return api.createAlbum(
            AlbumModelCreate(
                name = album.name,
                cover = album.cover,
                releaseDate = album.releaseDate,
                description = album.description,
                genre = album.genre,
                recordLabel = album.recordLabel,
            ),
        )?.toDomain()
    }

    suspend fun createAlbumToDB(album: AlbumEntity) {
        dao.insertAlbumsDao(listOf(album))
    }

    suspend fun deleteAlbumByIdFromApi(id: Int): Unit? {
        return api.deleteAlbumById(id)
    }

    suspend fun deleteAlbumByIdFromDB(id: Int) {
        dao.deleteAlbumByIdDao(id)
    }

    suspend fun addTrackToAlbumApi(albumId: Int, track: Track): Track? {
        return api.addTrackToAlbum(
            albumId,
            TrackModelCreate(
                name = track.name,
                duration = track.duration,
            ),
        )?.toDomain()
    }

    suspend fun addTrackToAlbumDB(track: TrackEntity) {
        dao.insertTracksDao(listOf(track))
    }

    suspend fun deleteTrackFromAlbumApi(albumId: Int, trackId: Int): Unit? {
        return api.deleteTrackFromAlbum(albumId, trackId)
    }

    suspend fun deleteTrackFromAlbumDB(trackId: Int) {
        dao.deleteTrackByIdDao(trackId)
    }
}
