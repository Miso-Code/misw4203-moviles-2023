package com.example.misw4203moviles2023.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.misw4203moviles2023.domain.performer.GetPerformers
import com.example.misw4203moviles2023.domain.performer.model.Performer
import kotlinx.coroutines.launch

class PerformerListViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext: Context = application.applicationContext

    val performerModel = MutableLiveData<List<Performer>?>()
    private val isLoading = MutableLiveData<Boolean>()

    var getPerformers = GetPerformers(applicationContext)
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
