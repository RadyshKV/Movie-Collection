package com.example.moviecollection.model

import com.example.moviecollection.model.entities.Movie

class RepositoryImpl : Repository {
    override fun getMovieDataFromServer(): Movie {
        return Movie()
    }

    override fun getMovieDataFromLocalStorage(): Movie {
        return Movie()
    }
}