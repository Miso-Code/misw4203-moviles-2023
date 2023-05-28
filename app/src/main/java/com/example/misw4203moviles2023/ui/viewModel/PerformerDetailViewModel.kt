package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.performer.GetPerformerById
import com.example.misw4203moviles2023.domain.performer.model.Performer
import kotlinx.coroutines.launch

private const val TIMESTAMPT_REGEX_END = 10

class PerformerDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext: Context = application.applicationContext

    val performerModel = MutableLiveData<Performer?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getPerformerById = GetPerformerById(applicationContext)
    fun onCreate(performerId: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPerformerById(performerId)

            for (i in result.albums.indices) {
                result.albums[i].releaseDate =
                    result.albums[i].releaseDate.substring(0, TIMESTAMPT_REGEX_END)
                        .split("-").reversed().joinToString("/")
            }

            performerModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}
