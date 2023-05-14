package com.example.misw4203moviles2023.data.database

import android.content.Context
import androidx.room.Room
import com.example.misw4203moviles2023.data.database.entities.AlbumEntity
import com.example.misw4203moviles2023.data.database.entities.AlbumWithTracksEntity
import com.example.misw4203moviles2023.data.database.entities.TrackEntity

class DataBaseService(applicationContext:Context) {

	private val DB_NAME = "misw4203moviles2023"

	private val db = Room.databaseBuilder(
		applicationContext,
		AlbumDatabase::class.java, DB_NAME
	).build()

	private val albumDao = db.getAlbumsDao()
	private val trackDao = db.getTracksDao()


	suspend fun getAllAlbumsDao (): List<AlbumWithTracksEntity> {
		val response = albumDao.getAllAlbums()
		return response ?: emptyList()
	}

	suspend fun getAlbumByIdDao(id: Int): AlbumWithTracksEntity {
		return albumDao.getAlbumById(id)
	}

	suspend fun deleteAlbumsDao () {
		val response =albumDao.deleteAllAlbums()
	}

	suspend fun insertAlbumsDao (albums:List<AlbumEntity>) {
		albumDao.insertAllAlbums(albums)
	}

	suspend fun getAllTracksDao (): List<TrackEntity> {
		val response =trackDao.getAllTracks()
		return response ?: emptyList()
	}

	suspend fun deleteTracksDao () {
		trackDao.deleteAllTracks()
	}

	suspend fun insertTracksDao (tracks:List<TrackEntity>) {
		trackDao.insertAllTracks(tracks)
	}
}