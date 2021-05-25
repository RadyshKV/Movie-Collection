package com.example.moviecollection.view

import IS_ADULT
import PREFERENCES
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviecollection.R
import com.example.moviecollection.adapters.VerticalFragmentAdapter
import com.example.moviecollection.databinding.MainFragmentBinding
import com.example.moviecollection.model.showSnackBar
import com.example.moviecollection.viewmodel.AppState
import com.example.moviecollection.viewmodel.MainViewModel
import com.geekbrains.weatherwithmvvm.interactors.strings_interactor.StringsInteractorImpl

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var adapter: VerticalFragmentAdapter? = null
    private var isDataSetRus: Boolean = true
    private var isAdult: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.verticalFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener { changeMovieDataSet() }
        viewModel.stringsInteractor = StringsInteractorImpl(requireContext())
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it as AppState) })
        isAdult = activity?.getSharedPreferences( PREFERENCES, Context.MODE_PRIVATE)?.getBoolean(IS_ADULT, false) ?: false
        viewModel.getMovieDataFromRemoteSourceRus(isAdult)
    }

    private fun changeMovieDataSet() = with(binding) {
        if (isDataSetRus) {
            viewModel.getMovieDataFromRemoteSourceWorld(isAdult)
            mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getMovieDataFromRemoteSourceRus(isAdult)
            mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    @Suppress("NAME_SHADOWING")
    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.SuccessListData -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                adapter = VerticalFragmentAdapter(this@MainFragment).apply {
                    setMovies(appState.movieData)
                    initCategories()
                }
                verticalFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                mainFragmentRootView.showSnackBar(
                        viewModel.stringsInteractor.errorStr,
                        viewModel.stringsInteractor.reloadStr,
                        { viewModel.getMovieDataFromRemoteSourceRus(isAdult) })
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}