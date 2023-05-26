package com.example.misw4203moviles2023.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.misw4203moviles2023.domain.collector.model.Collector

@Entity(tableName = "collector_table")
data class CollectorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "collector_id")
    val id: Int = 0,
    @ColumnInfo(name = "collector_name") val name: String,
    @ColumnInfo(name = "collector_telephone") val telephone: String,
    @ColumnInfo(name = "collector_email") var email: String,
)

fun Collector.toDataBase() = CollectorEntity(id, name, telephone, email)
