package com.example.misw4203moviles2023.data.database.dao

import android.content.Context
import androidx.room.Room
import com.example.misw4203moviles2023.data.database.AlbumDatabase
import com.example.misw4203moviles2023.data.database.entities.AlbumWithTracksEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val DB_NAME = "misw4203moviles2023"

class AlbumDaoService(private val context: Context) {

    private val provideRoom =
        Room.databaseBuilder(context.applicationContext, AlbumDatabase::class.java, DB_NAME).build()

    suspend fun getAlbums(): List<AlbumWithTracksEntity> {
        return withContext(Dispatchers.IO) {
            val response = provideRoom.getAlbumsDao().getAllAlbums()
            response
        }
    }

    suspend fun getAlbumById(id: Int): AlbumWithTracksEntity {
        return withContext(Dispatchers.IO) {
            val response = provideRoom.getAlbumsDao().getAlbumById(id)
            response
        }
    }
}
