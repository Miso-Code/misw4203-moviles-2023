package com.example.misw4203moviles2023.domain.album.model

import com.example.misw4203moviles2023.data.database.entities.AlbumEntity
import com.example.misw4203moviles2023.data.database.entities.AlbumWithTracksEntity
import com.example.misw4203moviles2023.data.database.entities.TrackEntity
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.AlbumModelNoTracks

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track>,
)

fun AlbumModel.toDomain() = Album(
    id,
    name,
    cover,
    releaseDate,
    description,
    genre,
    recordLabel,
    tracks.map { it.toDomain() },
)

fun AlbumModelNoTracks.toDomain() = Album(
    id,
    name,
    cover,
    releaseDate,
    description,
    genre,
    recordLabel,
    emptyList(),
)

fun AlbumModel.performerToDomain() = Album(
    id,
    name,
    cover,
    releaseDate,
    description,
    genre,
    recordLabel,
    emptyList(),
)

fun AlbumEntity.toDomain(trackList: List<TrackEntity>) = Album(
    id,
    name,
    cover,
    releaseDate,
    description,
    genre,
    recordLabel,
    tracks = trackList.map { it.toDomain() },
)

fun AlbumEntity.performerToDomain() = Album(
    id,
    name,
    cover,
    releaseDate,
    description,
    genre,
    recordLabel,
    tracks = emptyList(),
)

fun AlbumWithTracksEntity.toDomain() = Album(
    album.id,
    album.name,
    album.cover,
    album.releaseDate,
    album.description,
    album.genre,
    album.recordLabel,
    tracks = tracks.map { it.toDomain() },
)
