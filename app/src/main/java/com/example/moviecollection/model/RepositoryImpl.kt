package com.example.moviecollection.model

import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.rest_entities.GenreDTO
import com.example.moviecollection.model.rest_entities.MovieDetailDTO

class RepositoryImpl : Repository {

    private val genresDTO: List<GenreDTO>? by lazy { Loader.loadGenres() }

    override fun getMoviesDataFromServer(isRussian: Boolean): MutableList<Movie> {
        val movies: MutableList<Movie> = mutableListOf()
        val moviesDTO = Loader.loadMovies(isRussian)
        moviesDTO?.forEach { movieDTO ->
            movies.add(Movie(id = movieDTO.id, title = movieDTO.title, dateOfRelease = movieDTO.release_date, genre = getGenres(movieDTO.genre_ids, genresDTO as List<GenreDTO>)))
        }
        return movies
    }

    override fun getMovieDetailsDataFromServer(id: Long?): MovieDetailDTO? {
        return Loader.loadMovieDetails(id)
    }

    private fun getGenres(genre_ids: List<Int>?, genresDTO: List<GenreDTO>): List<String> {
        val result: MutableList<String> = mutableListOf()
        if (genre_ids != null) {
            for (genre_id in genre_ids) {
                result.add(getGenreString(genre_id, genresDTO))
            }
        }
        return result
    }

    private fun getGenreString(genreID: Int, genresDTO: List<GenreDTO>): String {
        for (genreDTO in genresDTO) {
            if (genreDTO.id == genreID) return genreDTO.name
        }
        return ""
    }

}