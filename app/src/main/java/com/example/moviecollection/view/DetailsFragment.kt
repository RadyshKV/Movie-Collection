package com.example.moviecollection.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviecollection.databinding.DetailsFragmentBinding
import com.example.moviecollection.model.entities.Movie

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieData = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        if (movieData != null) {
            binding.movieTitle.text = movieData.title
            binding.yearOfRelease.text = movieData.yearOfRelease.toString()
            binding.rating.text = movieData.rating.toString()
            binding.genre.text = movieData.genre
            binding.briefDescription.text = movieData.briefDescription
            binding.originCountry.text = movieData.originCountry
            binding.director.text = movieData.director
            binding.screenwriter.text = movieData.screenwriter
            binding.duration.text = movieData.duration.toString()
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}