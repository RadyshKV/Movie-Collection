package com.example.moviecollection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecollection.model.Repository
import com.example.moviecollection.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(), private val repositoryImpl: Repository = RepositoryImpl()) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getMovieData() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getMovieDataFromLocalStorage()))
        }.start()
    }
}