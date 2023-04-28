package com.example.misw4203_moviles_2023.domain.album

import com.example.misw4203_moviles_2023.data.AlbumRepository
import com.example.misw4203_moviles_2023.data.model.AlbumModel

class GetAlbumById {
    private val repository = AlbumRepository()

    suspend operator fun invoke(id:Int):AlbumModel = repository.getAlbumById(id)
}