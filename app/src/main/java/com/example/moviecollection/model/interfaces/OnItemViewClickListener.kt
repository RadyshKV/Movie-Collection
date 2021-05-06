package com.example.moviecollection.model.interfaces

import com.example.moviecollection.model.entities.Movie

interface OnItemViewClickListener {
    fun onItemViewClick(movie: Movie)
}
