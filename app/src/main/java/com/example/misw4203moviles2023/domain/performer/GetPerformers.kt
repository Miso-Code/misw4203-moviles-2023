package com.example.misw4203moviles2023.domain.performer

import com.example.misw4203moviles2023.data.PerformerRepository
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.data.database.entities.toTrack
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.performer.model.Performer

class GetPerformers {
    private val repository = PerformerRepository()

    // suspend operator fun invoke(): List<PerformerModel>? = repository.getPerformers()

    suspend operator fun invoke(): List<Performer> {
        val albums = repository.getPerformersFromApi()

        return if (albums.isNotEmpty()) {
            repository.clearAlbums()
            repository.insertAlbums(
                albums = albums.map { it.toDatabase() },
                tracks = albums.map { it.toTrack() },
            )
            return albums
        } else {
            repository.getPerformersFromDB()
        }
    }
}
