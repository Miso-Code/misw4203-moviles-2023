package com.example.misw4203moviles2023.domain.album

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.data.database.entities.toTrack
import com.example.misw4203moviles2023.domain.album.model.Album

class GetAlbums(context: Context) {
    private val repository = AlbumRepository(context)

    suspend operator fun invoke(): List<Album> {
        val albums = repository.getAllAlbumsFromApi()

        return if (albums.isNotEmpty()) {
            repository.clearAlbums()
            repository.insertAlbums(
                albums = albums.map { it.toDatabase() },
                tracks = albums.map { it.toTrack() },
            )
            return albums
        } else {
            val response = repository.getAllAlbumsFromDB()
            response
        }
    }
}
