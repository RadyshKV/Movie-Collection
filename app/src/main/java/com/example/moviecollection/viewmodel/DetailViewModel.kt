package com.example.moviecollection.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecollection.model.Repository
import com.example.moviecollection.model.RepositoryImpl
import com.example.moviecollection.model.rest.rest_entities.MovieDetailDTO
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalStateException


class DetailsViewModel : ViewModel(), LifecycleObserver {
    private val repository: Repository = RepositoryImpl()
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    lateinit var stringsInteractor: StringsInteractor

    fun loadData(id: Long?) {

        liveDataToObserve.value = AppState.Loading
        Thread {
            val data = repository.getMovieDetailsDataFromServer(id)
            liveDataToObserve.postValue(AppState.SuccessDetailsData(data))
        }.start()
    }

    fun loadDataAsync(id: Long?) {
        val callback = object: Callback<MovieDetailDTO>{
            override fun onResponse(
                call: Call<MovieDetailDTO>,
                response: Response<MovieDetailDTO>
            ) {
                if (response.isSuccessful){
                    liveDataToObserve.postValue(AppState.SuccessDetailsData(
                        response.body()
                    ))
                } else {
                    liveDataToObserve.postValue(AppState.Error(IllegalStateException()))
                }
            }

            override fun onFailure(call: Call<MovieDetailDTO>, t: Throwable) {
                t.printStackTrace()
            }

        }
        repository.getMovieDetailsDataFromServerAsync(id, callback)
    }
}