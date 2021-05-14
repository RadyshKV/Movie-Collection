package com.example.moviecollection.model

import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.rest_entities.MovieDetailDTO

interface Repository {
    fun getMoviesDataFromServer(isRussian: Boolean): MutableList<Movie>?
    fun getMovieDetailsDataFromServer(id: Long?): MovieDetailDTO?
}