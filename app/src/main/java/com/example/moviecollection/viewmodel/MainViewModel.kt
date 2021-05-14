package com.example.moviecollection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecollection.model.Repository
import com.example.moviecollection.model.RepositoryImpl
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractor
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(), private val repositoryImpl: Repository = RepositoryImpl()) : ViewModel() {

    private val LOAD_TIME: Long = 1000

    fun getLiveData() = liveDataToObserve

    lateinit var stringsInteractor: StringsInteractor

    fun getMovieDataFromRemoteSourceRus() = getDataFromRemoteSource(isRussian = true)

    fun getMovieDataFromRemoteSourceWorld() = getDataFromRemoteSource(isRussian = false)

    private fun getDataFromRemoteSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(repositoryImpl.getMoviesDataFromServer(isRussian)?.let { AppState.SuccessListData(it) })
        }.start()
    }
}