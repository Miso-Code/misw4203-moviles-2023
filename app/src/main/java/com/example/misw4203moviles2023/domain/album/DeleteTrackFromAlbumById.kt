package com.example.misw4203moviles2023.domain.album

import android.content.Context
import com.example.misw4203moviles2023.data.AlbumRepository

class DeleteTrackFromAlbumById(context: Context) {
    private val repository = AlbumRepository(context)

    suspend operator fun invoke(albumId: Int, trackId: Int) {
        repository.deleteTrackFromAlbumApi(albumId, trackId)
        repository.deleteTrackFromAlbumDB(trackId)
    }
}
