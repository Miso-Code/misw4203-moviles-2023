package com.example.misw4203moviles2023.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.misw4203moviles2023.data.database.entities.CollectorEntity

@Dao
interface CollectorDao {

	@Transaction
	@Query("SELECT * FROM collector_table ORDER BY collector_name DESC")
	suspend fun getAllCollectors(): List<CollectorEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAllCollectors(collectors: List<CollectorEntity>)

	@Query("Delete from  collector_table")
	suspend fun deleteAllCollectors()

	@Transaction
	@Query("SELECT * FROM collector_table WHERE collector_id=:id")
	suspend fun getCollector(id: Int): CollectorEntity
}
