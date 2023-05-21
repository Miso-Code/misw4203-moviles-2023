package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.album.GetAlbums
import com.example.misw4203moviles2023.domain.album.model.Album
import kotlinx.coroutines.launch

private const val TIMESTAMPT_REGEX_END = 10

class AlbumListViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext: Context = application.applicationContext
    val albumModel = MutableLiveData<List<Album>?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getAlbums = GetAlbums(applicationContext)
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAlbums()
            result.forEach {
                it.releaseDate =
                    it.releaseDate.substring(0, TIMESTAMPT_REGEX_END).split("-").reversed()
                        .joinToString("/")
            }
            val sortedResult = result.sortedByDescending { it.name }
            if (result.isNotEmpty()) {
                albumModel.postValue(sortedResult)
                isLoading.postValue(false)
            }
        }
    }
}
