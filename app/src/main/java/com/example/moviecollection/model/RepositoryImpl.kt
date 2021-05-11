package com.example.moviecollection.model

import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.entities.getRussianMovies
import com.example.moviecollection.model.entities.getWorldMovies

class RepositoryImpl : Repository {
    override fun getMovieDataFromServer() =  Movie()


    override fun getMovieDataFromLocalStorageRus() = getRussianMovies()


    override fun getMovieDataFromLocalStorageWorld() = getWorldMovies()

}