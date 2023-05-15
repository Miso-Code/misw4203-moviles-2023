package com.example.misw4203moviles2023.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["album_id", "performer_id"])
data class PerformerAlbumCrossRefEntity(
    @ColumnInfo(name = "album_id") val albumId: Int,
    @ColumnInfo(name = "performer_id") val performerId: Int,
)
