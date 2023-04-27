package com.example.misw4203_moviles_2023.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.misw4203_moviles_2023.data.model.AlbumModel
import com.example.misw4203_moviles_2023.domain.album.GetAlbums
import kotlinx.coroutines.launch

class AlbumListViewModel : ViewModel() {

    val albumModel = MutableLiveData<List<AlbumModel>?>()
    val isLoading = MutableLiveData<Boolean>()

    var getAlbums = GetAlbums()
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAlbums()
            result?.forEach {
                it.releaseDate =
                    it.releaseDate.substring(0, 10).split("-").reversed().joinToString("/")
            }
            if (!result.isNullOrEmpty()) {
                albumModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }
}