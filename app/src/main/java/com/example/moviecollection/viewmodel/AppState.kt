package com.example.moviecollection.viewmodel

import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.rest_entities.MovieDetailDTO

sealed class AppState {
    data class SuccessListData(val movieData: List<Movie>) : AppState()
    data class SuccessDetailsData(val movieData: MovieDetailDTO?) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}