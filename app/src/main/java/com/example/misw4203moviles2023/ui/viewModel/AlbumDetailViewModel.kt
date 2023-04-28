package com.example.misw4203moviles2023.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.domain.album.GetAlbumById
import kotlinx.coroutines.launch

private const val TIMESTAMPT_REGEX_END = 10

class AlbumDetailViewModel : ViewModel() {

    val albumModel = MutableLiveData<AlbumModel?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getAlbumById = GetAlbumById()
    fun onCreate(albumId: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAlbumById(albumId)
            result.releaseDate =
                result.releaseDate.substring(0, TIMESTAMPT_REGEX_END).split("-").reversed()
                    .joinToString("/")
            albumModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}
