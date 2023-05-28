package com.example.misw4203moviles2023.domain.performer.model

import com.example.misw4203moviles2023.data.database.entities.PerformerEntity
import com.example.misw4203moviles2023.data.database.entities.PerformerWithAlbums
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.performerToDomain

data class Performer(
    val id: Int,
    val name: String,
    val description: String,
    var image: String,
    var albums: List<Album>,
)

fun PerformerModel.toDomain() =
    Performer(id, name, description, image, albums.map { it.performerToDomain() })

fun PerformerModel.toDomainEmptyAlbums() =
    Performer(id, name, description, image, emptyList())

fun PerformerWithAlbums.toDomain() = Performer(
    performer.id,
    performer.name,
    performer.description,
    performer.image,
    albums.map { it.performerToDomain() },
)

fun PerformerEntity.toDomain() = Performer(id, name, description, image, emptyList())
