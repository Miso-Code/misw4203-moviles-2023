package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.album.GetAlbumById
import com.example.misw4203moviles2023.domain.album.model.Album
import kotlinx.coroutines.launch

private const val TIMESTAMPT_REGEX_END = 10

class AlbumDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext: Context = application.applicationContext
    val albumModel = MutableLiveData<Album?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getAlbumById = GetAlbumById(applicationContext)
    fun onCreate(albumId: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAlbumById(albumId)
            if (result != null) {
                result.releaseDate =
                    result.releaseDate.substring(0, TIMESTAMPT_REGEX_END).split("-").reversed()
                        .joinToString("/")
            }
            albumModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}
