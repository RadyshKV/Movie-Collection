package com.example.moviecollection

import Movie
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }


    private fun initView() {
        findViewById<Button>(R.id.button).setOnClickListener { testFun() }
    }


    private fun testFun() {
        val movie = Movie("Операция Ы", "Гайдай")
        val movieCopy = movie.copy()
        Toast.makeText(this, movieCopy.toString(), Toast.LENGTH_SHORT).show()
        val movieList = ArrayList<Movie>(3)
        movieList.add(Movie("первый", "1111"))
        movieList.add(Movie("второй", "2222"))
        movieList.add(Movie("третий", "3333"))
        for (movies in movieList) {
            println(movies.title)
        }

        for (i in 0..2 step 2) {
            println(movieList.get(i).producer)
        }

        for (i in 0 until movieList.size) {
            println(movieList.get(i).toString())
        }
    }
}

