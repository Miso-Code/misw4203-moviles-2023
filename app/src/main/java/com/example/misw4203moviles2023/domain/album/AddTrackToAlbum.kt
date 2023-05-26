package com.example.misw4203moviles2023.domain.album

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository
import com.example.misw4203moviles2023.data.database.entities.toDatabase
import com.example.misw4203moviles2023.domain.album.model.Track

class AddTrackToAlbum(context: Context) {
    private val repository = AlbumRepository(null, context)

    suspend operator fun invoke(albumId: Int, track: Track) {
        repository.addTrackToAlbumApi(albumId, track)
        repository.addTrackToAlbumDB(track.toDatabase(albumId))
    }
}
