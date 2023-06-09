package com.example.misw4203moviles2023.domain.performer

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.data.PerformerRepository
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.performer.model.Performer

class GetPerformers(context: Context) {
    private val performerRepository = PerformerRepository(context)
    private val albumRepository = AlbumRepository(context)

    suspend operator fun invoke(): List<Performer> {
        val performers: List<Performer> = performerRepository.getPerformersFromApi()
        val albums: List<Album> = albumRepository.getAllAlbumsFromApi()

        val performersWithAlbums: List<Performer> = performers.map { performer ->
            val performerAlbums = albums.filter { album ->
                album.id in performer.albums.map { it.id }
            }
            performer.copy(albums = performerAlbums)
        }

        return if (performersWithAlbums.isNotEmpty()) {
            performerRepository.clearAllPerformer()

            performerRepository.insertAllPerformer(performers)
            return performersWithAlbums
        } else {
            performerRepository.getPerformersFromDB()
        }
    }
}
