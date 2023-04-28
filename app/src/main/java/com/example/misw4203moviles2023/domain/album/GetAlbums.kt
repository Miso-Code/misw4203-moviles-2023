package com.example.misw4203moviles2023.domain.album

import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.data.model.AlbumModel

class GetAlbums {
    private val repository = AlbumRepository()

    suspend operator fun invoke(): List<AlbumModel>? = repository.getAllAlbums()
}
