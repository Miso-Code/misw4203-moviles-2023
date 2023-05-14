package com.example.misw4203moviles2023.data.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.misw4203moviles2023.domain.performer.model.Performer

data class PerformerWithAlbums(
	@Embedded val performer: PerformerEntity,
	@Relation(
		parentColumn = "performer_id",
		entityColumn = "album_id",
		associateBy = Junction(PerformerAlbumCrossRefEntity::class)
	)
	val albums: List<AlbumEntity>
)

fun Performer.toWithAlbumsDB() = PerformerWithAlbums(
	PerformerEntity(id, name, description, image),
	albums.map {
		AlbumEntity(
			it.id,
			it.name,
			it.cover,
			it.releaseDate,
			it.description,
			it.genre,
			it.recordLabel
		)
	}
)
