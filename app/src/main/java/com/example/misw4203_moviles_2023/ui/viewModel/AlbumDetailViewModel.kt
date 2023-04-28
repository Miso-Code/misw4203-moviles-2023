package com.example.misw4203_moviles_2023.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.misw4203_moviles_2023.data.model.AlbumModel
import com.example.misw4203_moviles_2023.domain.album.GetAlbumById
import kotlinx.coroutines.launch

class AlbumDetailViewModel : ViewModel() {

    val albumModel = MutableLiveData<AlbumModel?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getAlbumById = GetAlbumById()
    fun onCreate(albumId:Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAlbumById(albumId)
            if (result == null) {
                albumModel.postValue(null)
                isLoading.postValue(false)
            }
            else {
                result.releaseDate =
                    result.releaseDate.substring(0, 10).split("-").reversed().joinToString("/")
                albumModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }
}