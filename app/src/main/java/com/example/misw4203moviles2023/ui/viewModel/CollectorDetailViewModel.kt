package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.collector.GetCollectorById
import com.example.misw4203moviles2023.domain.collector.model.Collector
import kotlinx.coroutines.launch

class CollectorDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext: Context = application.applicationContext
    val collectorModel = MutableLiveData<Collector?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getCollectorById = GetCollectorById(applicationContext)

    fun onCreate(collectorId: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getCollectorById(collectorId)
            collectorModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}
