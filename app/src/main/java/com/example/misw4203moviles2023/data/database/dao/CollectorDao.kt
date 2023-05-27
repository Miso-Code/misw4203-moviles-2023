package com.example.misw4203moviles2023.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.misw4203moviles2023.data.database.entities.CollectorAlbumCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.CollectorEntity
import com.example.misw4203moviles2023.data.database.entities.CollectorPerformerCrossRefEntity
import com.example.misw4203moviles2023.data.database.entities.CollectorWithAlbumsAndPerformers

@Dao
interface CollectorDao {

    @Transaction
    @Query("SELECT * FROM collector_table ORDER BY collector_name DESC")
    suspend fun getAllCollectors(): List<CollectorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCollectors(collectors: List<CollectorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollector(collectors: CollectorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectorWithAlbum(collectorAlbumCrossRefEntity: CollectorAlbumCrossRefEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectorWithPerformer(collectorPerformerCrossRefEntity: CollectorPerformerCrossRefEntity)

    @Query("Delete from  collector_table")
    suspend fun deleteAllCollectors()

    @Transaction
    @Query("SELECT * FROM collector_table WHERE collector_id=:id")
    suspend fun getCollector(id: Int): CollectorWithAlbumsAndPerformers
}
