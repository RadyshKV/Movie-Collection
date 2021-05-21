package com.example.moviecollection.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class GenreDTO(

        @SerializedName("id")
        val id: Int = -1,

        @SerializedName("name")
        val name: String = ""
)
