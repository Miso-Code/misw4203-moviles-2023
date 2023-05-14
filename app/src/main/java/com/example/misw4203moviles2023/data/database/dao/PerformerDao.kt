package com.example.misw4203moviles2023.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.misw4203moviles2023.data.database.entities.PerformerAlbumCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerWithAlbums
import com.example.misw4203moviles2023.data.database.entities.TrackEntity

@Dao
interface PerformerDao {
    @Query("SELECT * FROM performer_table ORDER BY performer_name DESC")
    suspend fun getAllPerformers(): List<PerformerWithAlbums>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPerformersWithAlbums(performerAlbumsCrossRefEntity: List<PerformerAlbumCrossRefEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPerformers(performers: List<PerformerEntity>)

    @Query("Delete from  track_table")
    suspend fun deleteAllPerformers()
}
