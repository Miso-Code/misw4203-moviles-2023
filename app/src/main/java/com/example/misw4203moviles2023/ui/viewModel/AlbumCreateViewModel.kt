package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.album.CreateAlbums
import com.example.misw4203moviles2023.domain.album.model.Album
import kotlinx.coroutines.launch

class AlbumCreateViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext: Context = application.applicationContext
    val genres = MutableLiveData<List<String>?>()
    val recordLabels = MutableLiveData<List<String>?>()
    val isLoading = MutableLiveData<Boolean>()

    // private var _getAlbums = GetAlbums(applicationContext)

    var createAlbumService = CreateAlbums(applicationContext)
    fun onCreate() {
        viewModelScope.launch {
            // Ideally this should come from an API, but for now we will hardcode it,
            // we can obtain a list of genres and record labels from the current albums

            // val result = _getAlbums()
            // genres.postValue(result.map { it.genre }.distinct())
            // recordLabels.postValue(result.map { it.recordLabel }.distinct())

            val validGenres = listOf("Salsa", "Rock", "Folk", "Classical")
            val validRecordLabels = listOf("Sony Music", "Fania Records", "EMI", "Elektra", "Discos Fuentes")

            genres.postValue(validGenres)
            recordLabels.postValue(validRecordLabels)
        }
    }

    fun createAlbum(album: Album) {
        isLoading.postValue(true)
        viewModelScope.launch {
            createAlbumService(album)
            isLoading.postValue(false)
        }
    }
}
