package com.example.moviecollection.model.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class MovieDetailDTO(

        @SerializedName("budget")
        val budget: Long?,

        @SerializedName("overview")
        val overview: String?,

        @SerializedName("tagline")
        val tagline: String?,

        @SerializedName("runtime")
        val runtime: Int?,

        @SerializedName("popularity")
        val popularity: Double?,

        @SerializedName("poster_path")
        val posterPath: String?

)
