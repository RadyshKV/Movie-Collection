package com.example.moviecollection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecollection.model.RepositoryImpl
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.rest.rest_entities.GenreDTO
import com.example.moviecollection.model.rest.rest_entities.GenresDTO
import com.example.moviecollection.model.rest.rest_entities.MoviesDTO
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(),
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    private var genresDTO: List<GenreDTO>? = null
    lateinit var stringsInteractor: StringsInteractor

    fun getMovieDataFromRemoteSourceRus() = getDataFromRemoteSourceAsync(isRussian = true)

    fun getMovieDataFromRemoteSourceWorld() = getDataFromRemoteSourceAsync(isRussian = false)

    private fun getDataFromRemoteSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(
                repositoryImpl.getMoviesDataFromServer(isRussian)
                    .let { AppState.SuccessListData(it) })
        }.start()
    }

    private fun loadGenresFromRemoteSourceAsync(isRussian: Boolean) {
        val callback = object : Callback<GenresDTO> {
            override fun onResponse(
                call: Call<GenresDTO>,
                response: Response<GenresDTO>
            ) {
                if (response.isSuccessful) {
                    genresDTO = response.body()?.genres
                    getDataFromRemoteSourceAsync(isRussian)
                } else {
                    liveDataToObserve.postValue(AppState.Error(IllegalStateException()))
                }
            }

            override fun onFailure(call: Call<GenresDTO>, t: Throwable) {
                t.printStackTrace()
            }

        }
        repositoryImpl.getGenresDataFromServerAsync(callback)
    }

    private fun getDataFromRemoteSourceAsync(isRussian: Boolean) {
        genresDTO?.let {
            val callback = object : Callback<MoviesDTO> {
                override fun onResponse(
                    call: Call<MoviesDTO>,
                    response: Response<MoviesDTO>
                ) {
                    if (response.isSuccessful) {
                        val movies: MutableList<Movie> = mutableListOf()
                        response.body()?.movies?.forEach { movieDTO ->
                            movies.add(
                                Movie(
                                    id = movieDTO.id,
                                    title = movieDTO.title,
                                    dateOfRelease = movieDTO.releaseDate,
                                    genre = repositoryImpl.getGenres(
                                        movieDTO.genres,
                                        genresDTO
                                    )
                                )
                            )
                        }
                        liveDataToObserve.postValue(
                            AppState.SuccessListData(movies)
                        )
                    } else {
                        liveDataToObserve.postValue(AppState.Error(IllegalStateException()))
                    }
                }

                override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
                    t.printStackTrace()
                }
            }
            repositoryImpl.getMoviesDataFromServerAsync(isRussian, callback)
        } ?: loadGenresFromRemoteSourceAsync(isRussian)
    }

    companion object {
        const val LOAD_TIME: Long = 1000
    }
}