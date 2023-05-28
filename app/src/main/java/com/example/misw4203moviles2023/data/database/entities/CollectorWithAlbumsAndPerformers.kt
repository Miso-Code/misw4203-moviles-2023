package com.example.misw4203moviles2023.data.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CollectorWithAlbumsAndPerformers(
    @Embedded val collector: CollectorEntity,
    @Relation(
        parentColumn = "collector_id",
        entityColumn = "album_id",
        associateBy = Junction(CollectorAlbumCrossRefEntity::class),
    )
    val albums: List<AlbumEntity>,
    @Relation(
        parentColumn = "collector_id",
        entityColumn = "performer_id",
        associateBy = Junction(CollectorPerformerCrossRefEntity::class),
    )
    val performer: List<PerformerEntity>,
)
