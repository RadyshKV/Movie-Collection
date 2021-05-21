package com.example.moviecollection.model.rest.rest_interaction

import com.example.moviecollection.model.rest.rest_entities.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BackendAPI {
    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Long?,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<MovieDetailDTO>

    @GET("movie/top_rated")
    fun getMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<MoviesDTO>

    @GET("genre/movie/list")
    fun getGenresList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenresDTO>
}