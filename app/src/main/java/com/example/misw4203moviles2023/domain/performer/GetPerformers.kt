package com.example.misw4203moviles2023.domain.performer

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.data.PerformerRepository
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.data.database.entities.toTrack
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.performer.model.Performer

class GetPerformers (context: Context) {
    private val repository = PerformerRepository(null, context)
    private val albumRepository = AlbumRepository(null, context)

   // suspend operator fun invoke(): List<Performer>? = repository.getPerformersFromApi()

    suspend operator fun invoke(): List<Performer> {
        val performers = repository.getPerformersFromApi()

        return if (performers.isNotEmpty()) {
            repository.clearAllPerformer()

            repository.insertAllPerformer(performers)
            return performers
        } else {
            repository.getPerformersFromDB()
        }
    }
}
