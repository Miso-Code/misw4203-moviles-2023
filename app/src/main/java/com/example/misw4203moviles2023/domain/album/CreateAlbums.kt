package com.example.misw4203moviles2023.domain.album

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.domain.album.model.Album

class CreateAlbums(context: Context) {
    private val repository = AlbumRepository(context)

    suspend operator fun invoke(album: Album) {
        repository.createAlbumToToApi(album)
        repository.createAlbumToDB(album.toDatabase())
    }
}
