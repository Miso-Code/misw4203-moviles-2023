package com.example.misw4203moviles2023.domain.album

import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.data.model.AlbumModel

class GetAlbumById {
    private val repository = AlbumRepository()

    suspend operator fun invoke(id: Int): AlbumModel = repository.getAlbumById(id)
}
