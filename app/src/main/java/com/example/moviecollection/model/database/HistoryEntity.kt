package com.example.moviecollection.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = false) val id: Long?,
    val title: String?,
    val date: String
)
