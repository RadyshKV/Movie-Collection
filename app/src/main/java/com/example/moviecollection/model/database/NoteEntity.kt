package com.example.moviecollection.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = false) val id: Long?,
    val note: String?
    )