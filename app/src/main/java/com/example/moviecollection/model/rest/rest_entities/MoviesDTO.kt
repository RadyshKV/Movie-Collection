package com.example.moviecollection.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class MoviesDTO(

    @SerializedName("results")
    val movies: List<MovieDTO>

)
