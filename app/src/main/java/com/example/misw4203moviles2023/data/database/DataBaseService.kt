package com.example.misw4203moviles2023.data.database

import android.content.Context
import androidx.room.Room
import com.example.misw4203moviles2023.data.database.entities.AlbumEntity
import com.example.misw4203moviles2023.data.database.entities.AlbumWithTracksEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerAlbumCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerWithAlbums
import com.example.misw4203moviles2023.data.database.entities.TrackEntity

private const val DB_NAME = "misw4203moviles2023"

@Suppress("TooManyFunctions")
class DataBaseService(applicationContext: Context) {

	private val db = Room.databaseBuilder(
		applicationContext,
		AlbumDatabase::class.java,
		DB_NAME,
	).build()

	private val albumDao = db.getAlbumsDao()
	private val trackDao = db.getTracksDao()
	private val performerDao = db.getPerformerDao()

	suspend fun getAllAlbumsDao(): List<AlbumWithTracksEntity> {
		val response = albumDao.getAllAlbums()
		return response ?: emptyList()
	}

	suspend fun getAlbumByIdDao(id: Int): AlbumWithTracksEntity {
		return albumDao.getAlbumById(id)
	}

	suspend fun deleteAlbumsDao() {
		val response = albumDao.deleteAllAlbums()
	}

	suspend fun insertAlbumsDao(albums: List<AlbumEntity>) {
		albumDao.insertAllAlbums(albums)
	}

	suspend fun getAllTracksDao(): List<TrackEntity> {
		val response = trackDao.getAllTracks()
		return response ?: emptyList()
	}

	suspend fun deleteTracksDao() {
		trackDao.deleteAllTracks()
	}

	suspend fun insertTracksDao(tracks: List<TrackEntity>) {
		trackDao.insertAllTracks(tracks)
	}

	suspend fun insertPerformerDao(performer: List<PerformerEntity>) {
		performerDao.insertAllPerformers(performer)
	}

	suspend fun insertPerformerWithAlbumsDao(performerAlbum: List<PerformerAlbumCrossRefEntity>) {
		performerDao.insertAllPerformersWithAlbums(performerAlbum)
	}

	suspend fun insertPerformerWithAlbumDao(performerAlbum: PerformerAlbumCrossRefEntity) {
		performerDao.insertAllPerformerWithAlbum(performerAlbum)
	}

	suspend fun getAllPerformerDao(): List<PerformerWithAlbums> {
		val response = performerDao.getAllPerformers()
		return response ?: emptyList()
	}

	suspend fun getPerformerByIdDao(id: Int): PerformerWithAlbums {
		return performerDao.getPerformerById(id)
	}

	suspend fun deleteAllPerformerDao() {
		performerDao.deleteAllPerformers()
	}
}
