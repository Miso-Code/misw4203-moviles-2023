package com.example.misw4203moviles2023.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.misw4203moviles2023.domain.album.model.Track

@Entity(tableName = "track_table")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id")
    val id: Int,
    @ColumnInfo(name = "track_name") val name: String,
    @ColumnInfo(name = "track_duration") val duration: String,
    @ColumnInfo(name = "track_album_id") val albumId: Int?,
)

fun Track.toDatabase(albumId: Int?) = TrackEntity(id, name, duration, albumId)
