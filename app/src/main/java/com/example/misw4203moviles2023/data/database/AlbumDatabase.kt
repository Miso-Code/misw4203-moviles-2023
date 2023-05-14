package com.example.misw4203moviles2023.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.misw4203moviles2023.data.database.dao.AlbumDao
import com.example.misw4203moviles2023.data.database.dao.TrackDao
import com.example.misw4203moviles2023.data.database.entities.AlbumEntity
import com.example.misw4203moviles2023.data.database.entities.TrackEntity

@Database(entities = [AlbumEntity::class, TrackEntity::class], version = 2, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {
	abstract fun getAlbumsDao(): AlbumDao

	abstract fun getTracksDao(): TrackDao
}
