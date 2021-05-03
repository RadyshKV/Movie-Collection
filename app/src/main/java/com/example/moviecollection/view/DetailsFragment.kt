package com.example.moviecollection.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.moviecollection.databinding.DetailsFragmentBinding
import com.example.moviecollection.viewmodel.AppState
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val observer = Observer<Any> { renderData(it as AppState) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getMovieData()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.movieData
                loadingLayout.visibility = View.GONE
                setData(movieData)
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
                Snackbar
                        .make(mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModel.getMovieData() }
                        .show()
            }
        }
    }

    private fun setData(movieData: Movie) = with(binding) {
        movieTitle.text = movieData.title
        yearOfRelease.text = movieData.yearOfRelease
        rating.text = movieData.rating.toString()
        genre.text = movieData.genre
        briefDescription.text = movieData.briefDescription
        originCountry.text = movieData.originCountry
        director.text = movieData.director
        screenwriter.text = movieData.screenwriter
        duration.text = movieData.duration.toString()
    }
}