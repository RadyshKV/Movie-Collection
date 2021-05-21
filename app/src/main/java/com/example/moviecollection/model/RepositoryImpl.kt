package com.example.moviecollection.model

import LANGUAGE
import REGION_RU
import REGION_US
import com.example.moviecollection.BuildConfig
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.rest.rest_entities.*
import com.example.moviecollection.model.rest.rest_interaction.BackendRepo
import retrofit2.Callback

class RepositoryImpl : Repository {

    private val genresDTO: List<GenreDTO>? by lazy { Loader.loadGenres() }

    override fun getMoviesDataFromServer(isRussian: Boolean): MutableList<Movie> {
        val movies: MutableList<Movie> = mutableListOf()
        val moviesDTO = Loader.loadMovies(isRussian)
        moviesDTO?.forEach { movieDTO ->
            movies.add(Movie(
                id = movieDTO.id,
                title = movieDTO.title,
                dateOfRelease = movieDTO.releaseDate,
                genre = getGenres(
                    movieDTO.genres,
                    genresDTO as List<GenreDTO>)
            )
            )
        }
        return movies
    }

    override fun getMoviesDataFromServerAsync(isRussian: Boolean, callback: Callback<MoviesDTO>) {
        val region = if (isRussian) REGION_RU else REGION_US
        val page = 1
        BackendRepo.api.getMoviesList(BuildConfig.MOVIE_API_KEY, LANGUAGE, page, region).enqueue(callback)
    }

    override fun getGenresDataFromServerAsync(
        callback: Callback<GenresDTO>
    ) {
        BackendRepo.api.getGenresList(BuildConfig.MOVIE_API_KEY, LANGUAGE).enqueue(callback)
    }

    override fun getMovieDetailsDataFromServer(id: Long?): MovieDetailDTO? {
        return Loader.loadMovieDetails(id)
    }

    override fun getMovieDetailsDataFromServerAsync(id: Long?, callback: Callback<MovieDetailDTO>) {
        BackendRepo.api.getMovie(id, BuildConfig.MOVIE_API_KEY, LANGUAGE).enqueue(callback)
    }

    fun getGenres(genresList: List<Int>?, genresDTO: List<GenreDTO>?): List<String> {
        val result: MutableList<String> = mutableListOf()
        if (genresList != null) {
            for (genre in genresList) {
                result.add(getGenreString(genre, genresDTO))
            }
        }
        return result
    }

    private fun getGenreString(genreID: Int, genresDTO: List<GenreDTO>?): String {
        if (genresDTO != null) {
            for (genreDTO in genresDTO) {
                if (genreDTO.id == genreID) return genreDTO.name
            }
        }
        return ""
    }

}