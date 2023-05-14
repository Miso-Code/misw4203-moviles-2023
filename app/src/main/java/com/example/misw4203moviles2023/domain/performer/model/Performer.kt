package com.example.misw4203moviles2023.domain.performer.model

import com.example.misw4203moviles2023.data.database.entities.PerformerWithAlbums
import com.example.misw4203moviles2023.data.database.entities.toDataBase
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.toDomain

data class Performer(
	val id: Int,
	val name: String,
	val description: String,
	var image: String,
	val albums: List<Album>,
)

fun PerformerModel.toDomain() =
	Performer(id, name, description, image, albums = albums.map { it.toDomain() })

fun PerformerWithAlbums.toDomain() = Performer(
	performer.id,
	performer.name,
	performer.name,
	performer.image,
	albums.map { it.toDomain(emptyList()) })