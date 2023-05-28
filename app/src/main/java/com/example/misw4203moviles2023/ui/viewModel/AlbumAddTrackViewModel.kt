package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.album.AddTrackToAlbum
import com.example.misw4203moviles2023.domain.album.GetAlbumById
import com.example.misw4203moviles2023.domain.album.model.Album
import com.example.misw4203moviles2023.domain.album.model.Track
import kotlinx.coroutines.launch

class AlbumAddTrackViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext: Context = application.applicationContext
    val albumModel = MutableLiveData<Album?>()
    val isLoading = MutableLiveData<Boolean>()

    var getAlbumById = GetAlbumById(applicationContext)
    var addTrack = AddTrackToAlbum(applicationContext)
    fun onCreate(albumId: Int) {
        viewModelScope.launch {
            val result = getAlbumById(albumId)
            albumModel.postValue(result)
        }
    }

    fun addTrack(track: Track) {
        viewModelScope.launch {
            isLoading.postValue(true)
            addTrack(albumModel.value!!.id, track)
            isLoading.postValue(false)
        }
    }
}
