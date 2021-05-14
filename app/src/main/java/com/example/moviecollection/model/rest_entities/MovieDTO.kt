package com.example.moviecollection.model.rest_entities

data class MovieDTO(
        val id: Long?,
        val genre_ids: List<Int>?,
        val release_date: String?,
        val title: String?
)
