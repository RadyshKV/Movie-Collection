package com.example.moviecollection.model.rest_entities

data class MovieDetailDTO(
        val budget: Long?,
        val overview: String?,
        val tagline: String?,
        val runtime: Int?,
        val popularity: Double?,
)
