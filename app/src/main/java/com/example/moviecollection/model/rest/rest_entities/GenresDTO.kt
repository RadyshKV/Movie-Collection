package com.example.moviecollection.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class GenresDTO(

    @SerializedName("genres")
    val genres: List<GenreDTO>
)
