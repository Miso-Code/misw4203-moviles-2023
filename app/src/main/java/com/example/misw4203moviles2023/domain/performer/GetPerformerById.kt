package com.example.misw4203moviles2023.domain.performer

import android.content.Context
import android.util.Log
import com.example.misw4203moviles2023.data.PerformerRepository
import com.example.misw4203moviles2023.domain.performer.model.Performer

class GetPerformerById(context: Context) {
	private val repository = PerformerRepository(null, context)

	suspend operator fun invoke(id: Int): Performer {
		val performer = repository.getPerformerByIdFromApi(id)
		return if (performer !== null) {
			performer
		} else {
			val response = repository.getPerformerByIdFromDB(id)
			Log.d("getPerformerByIdFromApi",response.toString())
			response
		}
	}
}
