package com.example.misw4203moviles2023.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.misw4203moviles2023.data.database.entities.AlbumEntity
import com.example.misw4203moviles2023.data.database.entities.AlbumWithTracksEntity

@Dao
interface AlbumDao {

	@Transaction
	@Query("SELECT * FROM album_table ORDER BY album_name DESC")
	suspend fun getAllAlbums(): List<AlbumWithTracksEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAllAlbums(albums: List<AlbumEntity>)

	@Query("Delete from  album_table")
	suspend fun deleteAllAlbums()

	@Transaction
	@Query("SELECT * FROM album_table WHERE album_table.album_id = :id ")
	suspend fun getAlbumById(id: Int): AlbumWithTracksEntity
}
