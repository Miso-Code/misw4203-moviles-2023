package com.example.misw4203moviles2023.domain.album

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.domain.album.model.Album

class GetAlbumById(context: Context) {
    private val repository = AlbumRepository(context)

    suspend operator fun invoke(id: Int): Album? {
        val album = repository.getAlbumByIdFromApi(id)
        return if (album !== null) album else repository.getAlbumByIdFromDb(id)
    }
}
