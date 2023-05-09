package com.example.misw4203moviles2023.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.data.model.PerformerModel
import com.example.misw4203moviles2023.domain.performer.GetPerformers
import kotlinx.coroutines.launch

class PerformerListViewModel : ViewModel() {

    val performerModel = MutableLiveData<List<PerformerModel>?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getPerformers = GetPerformers()
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getPerformers()
            val sortedResult = result?.sortedByDescending { it.name }
            if (sortedResult?.isNotEmpty() == true) {
                performerModel.postValue(sortedResult)
                isLoading.postValue(false)
            }
        }
    }
}