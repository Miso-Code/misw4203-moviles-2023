package com.example.misw4203moviles2023.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["performer_id", "collector_id"])
data class CollectorPerformerCrossRefEntity(
    @ColumnInfo(name = "performer_id") val performerId: Int,
    @ColumnInfo(name = "collector_id") val collectorId: Int,
)
