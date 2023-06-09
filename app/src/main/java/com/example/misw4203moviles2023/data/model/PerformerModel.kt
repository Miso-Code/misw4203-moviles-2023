package com.example.misw4203moviles2023.data.model

data class PerformerModel(
    val id: Int,
    val name: String,
    val description: String,
    var image: String,
    val albums: List<AlbumModel>,
)
