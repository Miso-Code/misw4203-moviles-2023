package com.example.misw4203moviles2023.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.data.model.CollectorModel
import com.example.misw4203moviles2023.domain.collector.GetCollectors
import kotlinx.coroutines.launch

class CollectorListViewModel : ViewModel() {

    val collectorModel = MutableLiveData<List<CollectorModel>?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getCollectors = GetCollectors()
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getCollectors()
            val sortedResult = result?.sortedByDescending { it.name }
            if (result?.isNotEmpty() == true) {
                collectorModel.postValue(sortedResult)
                isLoading.postValue(false)
            }
        }
    }
}
