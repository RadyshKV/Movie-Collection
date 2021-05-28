package com.example.moviecollection.view

import IMAGE_BASE_PATH
import android.annotation.SuppressLint
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
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractorImpl
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding

    private lateinit var movie: Movie


    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stringsInteractor = StringsInteractorImpl(requireContext())
        movie = arguments?.getParcelable(BUNDLE_EXTRA) ?: Movie()
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
                        loadImage(appState.movieData?.posterPath)
                    }
                    is AppState.SuccessNoteData -> {
                        if (appState.noteData.size > 0){
                            note.setText(appState.noteData[0].note)
                        }
                    }
                }
            })
            viewModel.loadDataAsync(movie.id)
            viewModel.loadNoteFromDB(movie.id)
        }
    }

    override fun onDestroy() = with(binding) {
        viewModel.saveNoteToDB(movie.id, note.text.toString())
        super.onDestroy()
    }

    private fun loadImage(url: String?) = with(binding){
        Picasso.get()
            .load("$IMAGE_BASE_PATH$url")
            .fit()
            .into(poster)
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }
}