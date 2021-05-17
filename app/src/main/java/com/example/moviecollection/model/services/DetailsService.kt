package com.example.moviecollection.model.services

import DETAILS_BUDGET_EXTRA
import DETAILS_DATA_EMPTY_EXTRA
import DETAILS_INTENT_EMPTY_EXTRA
import DETAILS_INTENT_FILTER
import DETAILS_LOAD_RESULT_EXTRA
import DETAILS_OVERVIEW_EXTRA
import DETAILS_POPULARITY_EXTRA
import DETAILS_REQUEST_ERROR_EXTRA
import DETAILS_REQUEST_ERROR_MESSAGE_EXTRA
import DETAILS_RESPONSE_EMPTY_EXTRA
import DETAILS_RESPONSE_SUCCESS_EXTRA
import DETAILS_RUNTIME_EXTRA
import DETAILS_TAGLINE_EXTRA
import DETAILS_URL_MALFORMED_EXTRA
import MOVIE_ID
import REQUEST_GET
import REQUEST_TIMEOUT
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviecollection.BuildConfig
import com.example.moviecollection.model.Loader
import com.example.moviecollection.model.rest_entities.MovieDetailDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsService(name: String = "DetailService") : JobIntentService() {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    override fun onHandleWork(intent: Intent) {

        val id = intent.getLongExtra(MOVIE_ID, 0L)
        if (id == 0L) {
            onEmptyData()
        } else {
            loadMovieDetailsFromService(id)
        }
    }


    private fun loadMovieDetailsFromService(id: Long?) {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${id}?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_GET
                urlConnection.readTimeout = REQUEST_TIMEOUT
                val bufferedReader =
                    BufferedReader(InputStreamReader(urlConnection.inputStream))
                val movieDetailDTO =
                    Gson().fromJson(Loader.getLines(bufferedReader), MovieDetailDTO::class.java)
                onResponse(movieDetailDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

private fun onResponse(movieDetailDTO: MovieDetailDTO) {
    movieDetailDTO.let {
        onSuccessResponse(it.budget, it.overview, it.tagline, it.runtime, it.popularity)
    }
}

private fun onSuccessResponse(budget: Long?, overview: String?, tagline: String?, runtime: Int?, popularity: Double?) {
    putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
    broadcastIntent.putExtra(DETAILS_BUDGET_EXTRA, budget)
    broadcastIntent.putExtra(DETAILS_OVERVIEW_EXTRA, overview)
    broadcastIntent.putExtra(DETAILS_TAGLINE_EXTRA, tagline)
    broadcastIntent.putExtra(DETAILS_RUNTIME_EXTRA, runtime)
    broadcastIntent.putExtra(DETAILS_POPULARITY_EXTRA, popularity)
    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
}

private fun onMalformedURL() {
    putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
}

private fun onErrorRequest(error: String) {
    putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
    broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
}

private fun onEmptyResponse() {
    putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
}

private fun onEmptyIntent() {
    putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
}

private fun onEmptyData() {
    putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
}

private fun putLoadResult(result: String) {
    broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
}

companion object {
    fun start(context: Context, intent: Intent) {
        enqueueWork(context, DetailsService::class.java, 3322, intent)
    }
}
}