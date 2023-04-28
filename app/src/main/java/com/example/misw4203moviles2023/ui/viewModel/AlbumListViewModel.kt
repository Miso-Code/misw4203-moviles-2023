package com.example.misw4203moviles2023.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.data.model.AlbumModel
import com.example.misw4203moviles2023.domain.album.GetAlbums
import kotlinx.coroutines.launch

private const val TIMESTAMPT_REGEX_END = 10

class AlbumListViewModel : ViewModel() {

    val albumModel = MutableLiveData<List<AlbumModel>?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getAlbums = GetAlbums()
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAlbums()
            result?.forEach {
                it.releaseDate =
                    it.releaseDate.substring(0, TIMESTAMPT_REGEX_END).split("-").reversed()
                        .joinToString("/")
            }
            val sortedResult = result?.sortedByDescending { it.name }
            if (result?.isNotEmpty() == true) {
                albumModel.postValue(sortedResult)
                isLoading.postValue(false)
            }
        }
    }
}
