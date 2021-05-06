package com.example.moviecollection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecollection.model.Repository
import com.example.moviecollection.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(), private val repositoryImpl: Repository = RepositoryImpl()) : ViewModel() {

    val LOAD_TIME: Long = 1000

    fun getLiveData() = liveDataToObserve

    fun getMovieDataFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getMovieDataFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getMovieDataFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(LOAD_TIME)
            liveDataToObserve.postValue(AppState.Success(if (isRussian) repositoryImpl.getMovieDataFromLocalStorageRus() else repositoryImpl.getMovieDataFromLocalStorageWorld()))
        }.start()
    }
}