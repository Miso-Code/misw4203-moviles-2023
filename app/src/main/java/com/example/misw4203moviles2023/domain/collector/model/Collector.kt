package com.example.misw4203moviles2023.domain.collector.model

import com.example.misw4203moviles2023.data.database.entities.CollectorEntity
import com.example.misw4203moviles2023.data.database.entities.CollectorWithAlbumsAndPerformers
import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.performerToDomain
import com.example.misw4203moviles2023.domain.performer.model.Performer
import com.example.misw4203moviles2023.domain.performer.model.toDomain
import com.example.misw4203moviles2023.domain.performer.model.toDomainEmptyAlbums

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val albums: List<Album>? = emptyList(),
    val performers: List<Performer>? = emptyList(),
)

fun CollectorModel.toDomain() = Collector(
    id,
    name,
    telephone,
    email,
    albums?.map { it.performerToDomain() },
    performers?.map { it.toDomainEmptyAlbums() },
)

fun CollectorEntity.toDomain() = Collector(id, name, telephone, email)

fun CollectorWithAlbumsAndPerformers.toDomain() = Collector(
    collector.id,
    collector.name,
    collector.telephone,
    collector.email,
    albums.map { it.performerToDomain() },
    performer.map { it.toDomain() },
)
