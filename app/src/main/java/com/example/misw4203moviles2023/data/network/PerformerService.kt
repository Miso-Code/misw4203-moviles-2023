package com.example.misw4203moviles2023.data.network

import com.example.misw4203moviles2023.core.RetrofitHelper
import com.example.misw4203moviles2023.data.model.PerformerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class PerformerService {
	private val retrofit = RetrofitHelper.getRetrofit()
	suspend fun getPerformers(): List<PerformerModel> {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(PerformerApiClient::class.java).getPerformers()
				response.body() ?: emptyList()
			} catch (e: IOException) {
				println("Error: ${e.message} : ${e.stackTrace}")
				emptyList()
			}
		}
	}

	suspend fun getPerformerById(id: Int): PerformerModel? {
		return withContext(Dispatchers.IO) {
			try {
				val response = retrofit.create(PerformerApiClient::class.java).getPerformerById(id)
				response.body() ?: PerformerModel(0, "", "", "", emptyList())
			} catch (e: IOException) {
				println("Error: ${e.message} : ${e.stackTrace}")
				null
			}
		}
	}
}
