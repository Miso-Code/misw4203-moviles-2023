package com.example.misw4203moviles2023.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.domain.performer.GetPerformerById
import kotlinx.coroutines.launch

private const val TIMESTAMPT_REGEX_END = 10

class PerformerDetailViewModel : ViewModel() {

    val performerModel = MutableLiveData<PerformerModel?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getPerformerById = GetPerformerById()
    fun onCreate(performerId: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPerformerById(performerId)
            result.albums.forEach {
                it.releaseDate =
                    it.releaseDate.substring(0, TIMESTAMPT_REGEX_END).split("-").reversed()
                        .joinToString("/")
            }
            performerModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}
