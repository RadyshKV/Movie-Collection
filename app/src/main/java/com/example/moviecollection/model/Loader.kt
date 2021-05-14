package com.example.moviecollection.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviecollection.BuildConfig
import com.example.moviecollection.model.rest_entities.GenreDTO
import com.example.moviecollection.model.rest_entities.MovieDTO
import com.example.moviecollection.model.rest_entities.MovieDetailDTO
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object Loader {
    fun loadMovies(isRussian: Boolean): List<MovieDTO>? {
        try {
            val region = if (isRussian) "&region=RU" else ""
            val uri = URL("https://api.themoviedb.org/3/movie/top_rated?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU&page=1${region}")
            return loadArrayDTO(uri, "results")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    fun loadGenres(): List<GenreDTO>? {
        try {
            val uri = URL("https://api.themoviedb.org/3/genre/movie/list?language=ru-RU&api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU")
            return loadArrayDTO(uri, "genres")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }


    private inline fun <reified J : Any> loadArrayDTO(uri: URL, sectionName: String): List<J>? {
        lateinit var urlConnection: HttpsURLConnection
        try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val lines = getLines(bufferedReader)
            val resultJSON = JSONObject(lines)
            val arrayJSON = resultJSON.getJSONArray(sectionName)
            val listDTO = mutableListOf<J>()
            for (i in 0 until arrayJSON.length()) {
                listDTO.add(Gson().fromJson(arrayJSON.get(i).toString(), J::class.java))
            }
            return listDTO
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection.disconnect()
        }
        return null
    }

    inline fun <reified J : Any> loadDTO(uri: URL): J? {
        lateinit var urlConnection: HttpsURLConnection
        try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            return Gson().fromJson(getLines(bufferedReader), J::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            urlConnection.disconnect()
        }
        return null
    }

    fun getLines(reader: BufferedReader): String {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            getLinesForOld(reader)
        } else {
            getLinesForNew(reader)
        }
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }
        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLinesForNew(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}

fun loadMovieDetails(id: Long?): MovieDetailDTO? {
    try {
        val uri = URL("https://api.themoviedb.org/3/movie/${id}?api_key=${BuildConfig.MOVIE_API_KEY}&language=ru-RU")
        return Loader.loadDTO(uri)
    } catch (e: MalformedURLException) {
        e.printStackTrace()
    }
    return null
}