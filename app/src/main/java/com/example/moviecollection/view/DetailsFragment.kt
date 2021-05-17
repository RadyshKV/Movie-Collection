package com.example.moviecollection.view

import BUDGET_INVALID
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
import POPULARITY_INVALID
import RUNTIME_INVALID
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviecollection.databinding.DetailsFragmentBinding
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.rest_entities.MovieDetailDTO
import com.example.moviecollection.model.services.DetailsService
import com.example.moviecollection.model.showSnackBar
import com.example.moviecollection.viewmodel.AppState
import com.example.moviecollection.viewmodel.DetailsViewModel
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractor
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractorImpl
import kotlinx.android.synthetic.main.details_fragment.*

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding

    private lateinit var movie: Movie
    val stringsInteractor: StringsInteractor by lazy { StringsInteractorImpl(requireContext())}

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
        movie = arguments?.getParcelable(BUNDLE_EXTRA) ?: Movie()
        startDetailsService()

       /* arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { movie ->
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

        }*/
    }

    private fun startDetailsService(){
        val intent = Intent(requireContext(), DetailsService::class.java).apply {
            putExtra(
                MOVIE_ID,
                movie.id
            )
        }
        DetailsService.start(requireContext(), intent)
    }

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> errorProcess()
                DETAILS_DATA_EMPTY_EXTRA -> errorProcess()
                DETAILS_RESPONSE_EMPTY_EXTRA -> errorProcess()
                DETAILS_REQUEST_ERROR_EXTRA -> errorProcess()
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> errorProcess()
                DETAILS_URL_MALFORMED_EXTRA -> errorProcess()
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    MovieDetailDTO(
                            intent.getLongExtra(DETAILS_BUDGET_EXTRA, BUDGET_INVALID),
                            intent.getStringExtra(DETAILS_OVERVIEW_EXTRA),
                            intent.getStringExtra(DETAILS_TAGLINE_EXTRA),
                            intent.getIntExtra(DETAILS_RUNTIME_EXTRA, RUNTIME_INVALID),
                            intent.getDoubleExtra(DETAILS_POPULARITY_EXTRA, POPULARITY_INVALID)
                    )
                )
                else -> errorProcess()
            }
        }
    }

    private fun renderData(movieDetailDTO: MovieDetailDTO) = with(binding) {
        mainView.visibility = View.VISIBLE
        loadingLayout.visibility = View.GONE

        val budgetDTO = movieDetailDTO.budget
        val overviewDTO = movieDetailDTO.overview
        val taglineDTO = movieDetailDTO.tagline
        val runtimeDTO = movieDetailDTO.runtime
        val popularityDTO = movieDetailDTO.popularity
        if (budgetDTO == BUDGET_INVALID || runtimeDTO == RUNTIME_INVALID || popularityDTO == POPULARITY_INVALID || overviewDTO == null || taglineDTO == null) {
            errorProcess()
        } else {
            movieTitle.text = movie.title
            dateOfRelease.text = movie.dateOfRelease
            genre.text = movie.genre.toString().removePrefix("[").removeSuffix("]")

            popularity.text = popularityDTO.toString()
            tagline.text = taglineDTO
            overview.text = overviewDTO
            budget.text = budgetDTO.toString()
            runtime.text = runtimeDTO.toString()
        }
    }

    private fun errorProcess(){
        loadingLayout.visibility = View.GONE
        detailsFragmentRootView.showSnackBar(
            stringsInteractor.errorStr,
            stringsInteractor.reloadStr,
            { startDetailsService() })

    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(loadResultsReceiver)
        super.onStop()
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"
        fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }
    }
}