package com.example.misw4203moviles2023.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.misw4203moviles2023.domain.album.model.Album

data class AlbumWithTracksEntity(
	@Embedded val album: AlbumEntity,
	@Relation(
		parentColumn = "album_id",
		entityColumn = "track_album_id",
	)
	val tracks: List<TrackEntity>,
)

fun Album.toDataBase() = AlbumWithTracksEntity(
	AlbumEntity(
		id,
		name,
		cover,
		releaseDate,
		description,
		genre,
		recordLabel,
	),
	tracks.map { it.toDatabase(id) },
)
