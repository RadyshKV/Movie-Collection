package com.example.moviecollection.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class MovieDTO(

        @SerializedName("id")
        val id: Long?,

        @SerializedName("adult")
        val adultContent: Boolean?,

        @SerializedName("genre_ids")
        val genres: List<Int>?,

        @SerializedName("release_date")
        val releaseDate: String?,

        @SerializedName("title")
        val title: String?
)
