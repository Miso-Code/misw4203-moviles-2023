package com.example.misw4203moviles2023.domain.album.model

import com.example.misw4203moviles2023.data.database.entities.TrackEntity
import com.example.misw4203moviles2023.data.model.TrackModel

data class Track(
    val id: Int,
    val name: String,
    val duration: String,
)

fun TrackModel.toDomain() = Track(id, name, duration)
fun TrackEntity.toDomain() = Track(id, name, duration)
