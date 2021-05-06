package com.example.moviecollection.model

import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.entities.getRussianMovies
import com.example.moviecollection.model.entities.getWorldMovies

class RepositoryImpl : Repository {
    override fun getMovieDataFromServer(): Movie {
        return Movie()
    }

    override fun getMovieDataFromLocalStorageRus(): List<Movie> {
        return getRussianMovies()
    }

    override fun getMovieDataFromLocalStorageWorld(): List<Movie> {
        return getWorldMovies()
    }
}