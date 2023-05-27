package com.example.misw4203moviles2023.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["album_id", "collector_id"])
data class CollectorAlbumCrossRefEntity(
    @ColumnInfo(name = "album_id") val albumId: Int,
    @ColumnInfo(name = "collector_id") val collectorId: Int,
)
