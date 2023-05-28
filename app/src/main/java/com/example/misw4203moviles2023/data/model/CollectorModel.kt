package com.example.misw4203moviles2023.data.model

data class CollectorModel(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    var albums: List<AlbumModel>? = emptyList(),
    var performers: List<PerformerModel>? = emptyList(),
)
data class CollectorAlbumModel(val collector: CollectorModel, val album: AlbumModel)
