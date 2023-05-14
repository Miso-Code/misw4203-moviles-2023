package com.example.misw4203moviles2023.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.misw4203moviles2023.domain.album.model.Album

@Entity(tableName = "album_table")
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "album_id")
    val id: Int = 0,
    @ColumnInfo(name = "album_name") val name: String,
    @ColumnInfo(name = "album_cover") val cover: String,
    @ColumnInfo(name = "album_release_date") var releaseDate: String,
    @ColumnInfo(name = "album_description") val description: String,
    @ColumnInfo(name = "album_genre") val genre: String,
    @ColumnInfo(name = "album_record_label") val recordLabel: String,
)

fun Album.toDatabase() = AlbumEntity(id, name, cover, releaseDate, description, genre, recordLabel)

fun Album.toTrack() = tracks.map { TrackEntity(it.id, it.name, it.duration, id) }
