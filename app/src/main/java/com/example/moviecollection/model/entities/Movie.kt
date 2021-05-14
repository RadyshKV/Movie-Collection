package com.example.moviecollection.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val id: Long? = 0,
        val title: String? = "Фильм",
        val dateOfRelease: String? = "дата",
        val genre: List<String>? = listOf("Жанр1", "жанр2"),
) : Parcelable


