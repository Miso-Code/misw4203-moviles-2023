package com.example.misw4203moviles2023.domain.collector.model

import com.example.misw4203moviles2023.data.database.entities.CollectorEntity
import com.example.misw4203moviles2023.data.model.CollectorModel

data class Collector(val id: Int, val name: String, val telephone: String, val email: String)

fun CollectorModel.toDomain() = Collector(id, name, telephone, email)

fun CollectorEntity.toDomain() = Collector(id, name, telephone, email)
