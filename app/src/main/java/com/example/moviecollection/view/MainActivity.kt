package com.example.moviecollection.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviecollection.R
import com.example.moviecollection.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance())
                    .commitNow()
        }
    }
}