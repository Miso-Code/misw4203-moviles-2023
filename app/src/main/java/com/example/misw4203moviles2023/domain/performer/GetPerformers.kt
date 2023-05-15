package com.example.misw4203moviles2023.domain.performer

import android.content.Context
import com.example.misw4203moviles2023.data.PerformerRepository
import com.example.misw4203moviles2023.domain.performer.model.Performer

class GetPerformers(context: Context) {
    private val repository = PerformerRepository(null, context)

    // suspend operator fun invoke(): List<Performer>? = repository.getPerformersFromApi()

    suspend operator fun invoke(): List<Performer> {
        val performers = repository.getPerformersFromApi()

        return if (performers.isNotEmpty()) {
            repository.clearAllPerformer()

            repository.insertAllPerformer(performers)
            return performers
        } else {
            repository.getPerformersFromDB()
        }
    }
}
