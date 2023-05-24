package com.example.misw4203moviles2023.domain.album

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository

class DeleteAlbumById(context: Context) {
    private val repository = AlbumRepository(null, context)

    suspend operator fun invoke(id: Int) {
        repository.deleteAlbumByIdFromApi(id)
        repository.deleteAlbumByIdFromDB(id)
    }
}
