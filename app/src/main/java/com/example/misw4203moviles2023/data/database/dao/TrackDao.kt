package com.example.misw4203moviles2023.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.misw4203moviles2023.data.database.entities.TrackEntity

@Dao
interface TrackDao {
	@Query("SELECT * FROM track_table ORDER BY track_name DESC")
	suspend fun getAllTracks(): List<TrackEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAllTracks(tracks: List<TrackEntity>)

	@Query("Delete from  track_table")
	suspend fun deleteAllTracks()
}
