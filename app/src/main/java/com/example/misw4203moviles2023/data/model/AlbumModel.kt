package com.example.misw4203moviles2023.data.model

data class AlbumModel(
    val id: Int,
    val name: String,
    val cover: String,
    var releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<TrackModel>,
)
