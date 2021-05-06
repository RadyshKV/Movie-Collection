package com.example.moviecollection.model

import com.example.moviecollection.model.entities.Movie

interface Repository {
    fun getMovieDataFromServer(): Movie
    fun getMovieDataFromLocalStorageRus(): List<Movie>
    fun getMovieDataFromLocalStorageWorld(): List<Movie>
}