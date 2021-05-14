package com.geekbrains.weatherwithmvvm.interactors.strings_interactor

import android.content.Context
import com.example.moviecollection.R


class StringsInteractorImpl(private val context: Context) : StringsInteractor {
    override val errorStr = context.getString(R.string.error)
    override val reloadStr = context.getString(R.string.reload)
}