package com.example.moviecollection.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.moviecollection.databinding.DetailsFragmentBinding
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.showSnackBar
import com.example.moviecollection.viewmodel.AppState
import com.example.moviecollection.viewmodel.DetailsViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { movie ->
            with(binding) {
                movieTitle.text = movie.title
                dateOfRelease.text = movie.dateOfRelease
                genre.text = movie.genre.toString().removePrefix("[").removeSuffix("]")
                viewModel.liveDataToObserve.observe(this@DetailsFragment, { appState ->
                    when (appState) {
                        is AppState.Error -> {
                            loadingLayout.visibility = View.GONE
                            detailsFragmentRootView.showSnackBar(
                                    viewModel.stringsInteractor.errorStr,
                                    viewModel.stringsInteractor.reloadStr,
                                    { viewModel.loadData(movie.id) })
                        }
                        AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE
                        is AppState.SuccessDetailsData -> {
                            loadingLayout.visibility = View.GONE
                            popularity.text = appState.movieData?.popularity.toString()
                            tagline.text = appState.movieData?.tagline
                            overview.text = appState.movieData?.overview
                            budget.text = appState.movieData?.budget.toString()
                            runtime.text = appState.movieData?.runtime.toString()
                        }
                    }
                })
                viewModel.loadData(movie.id)
            }

        }
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }
}