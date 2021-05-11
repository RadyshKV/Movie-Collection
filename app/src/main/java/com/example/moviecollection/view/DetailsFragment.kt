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
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { movie ->
            binding.movieTitle.text = movie.title
            binding.yearOfRelease.text = movie.yearOfRelease.toString()
            binding.rating.text = movie.rating.toString()
            binding.genre.text = movie.genre
            binding.briefDescription.text = movie.briefDescription
            binding.originCountry.text = movie.originCountry
            binding.director.text = movie.director
            binding.screenwriter.text = movie.screenwriter
            binding.duration.text = movie.duration.toString()
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }
}