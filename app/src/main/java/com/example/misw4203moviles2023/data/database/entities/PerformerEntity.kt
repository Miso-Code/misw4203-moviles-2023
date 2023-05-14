package com.example.misw4203moviles2023.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.Track
import com.example.misw4203moviles2023.domain.performer.model.Performer

@Entity(tableName = "performer_table")
data class PerformerEntity(
	@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "performer_id") val id: Int = 0,
	@ColumnInfo(name = "performer_name") val name: String,
	@ColumnInfo(name = "performer_description") val description: String,
	@ColumnInfo(name = "performer_image") val image: String,
)

fun Performer.toDatabase() = PerformerEntity(id, name, description, image)
