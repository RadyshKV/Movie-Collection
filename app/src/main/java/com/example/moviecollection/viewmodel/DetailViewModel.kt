package com.example.moviecollection.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecollection.model.Repository
import com.example.moviecollection.model.RepositoryImpl
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractor


class DetailsViewModel : ViewModel(), LifecycleObserver {
    private val repository: Repository = RepositoryImpl()
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()


    fun loadData(id: Long?) {

        liveDataToObserve.value = AppState.Loading
        Thread {
            val data = repository.getMovieDetailsDataFromServer(id)
            liveDataToObserve.postValue(AppState.SuccessDetailsData(data))
        }.start()
    }
}