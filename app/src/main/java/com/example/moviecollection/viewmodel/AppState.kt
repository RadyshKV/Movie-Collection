package com.example.moviecollection.viewmodel

import com.example.moviecollection.model.entities.Movie

sealed class AppState {
    data class Success(val movieData: Movie) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}