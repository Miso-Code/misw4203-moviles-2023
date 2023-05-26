package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.collector.GetCollectors
import com.example.misw4203moviles2023.domain.collector.model.Collector
import kotlinx.coroutines.launch

class CollectorListViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext: Context = application.applicationContext
    val collectorModel = MutableLiveData<List<Collector>?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getCollectors = GetCollectors(applicationContext)
    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getCollectors()
            val sortedResult = result.sortedByDescending { it.name }
            if (result.isNotEmpty()) {
                collectorModel.postValue(sortedResult)
                isLoading.postValue(false)
            }
        }
    }
}
