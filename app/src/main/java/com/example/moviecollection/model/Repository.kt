package com.example.moviecollection.model

import com.example.moviecollection.model.database.HistoryEntity
import com.example.moviecollection.model.database.NoteEntity
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.rest.rest_entities.*
import retrofit2.Callback

interface Repository {
    fun getMoviesDataFromServer(isRussian: Boolean): MutableList<Movie>?
    fun getMoviesDataFromServerAsync(isRussian: Boolean, callback: Callback<MoviesDTO>)
    fun getGenresDataFromServerAsync(callback: Callback<GenresDTO>)
    fun getMovieDetailsDataFromServer(id: Long?): MovieDetailDTO?
    fun getMovieDetailsDataFromServerAsync(id: Long?, callback: Callback<MovieDetailDTO>)
    fun getAllHistory(): List<HistoryEntity>
    fun saveEntity(movie: MovieDetailDTO?)
    fun getNoteFromDB(id: Long?): List<NoteEntity>
}