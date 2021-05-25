package com.example.moviecollection.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviecollection.R
import com.example.moviecollection.adapters.HistoryAdapter
import com.example.moviecollection.databinding.HistoryFragmentBinding
import com.example.moviecollection.model.showSnackBar
import com.example.moviecollection.viewmodel.AppState
import com.example.moviecollection.viewmodel.HistoryViewModel

class HistoryFragment: Fragment() {
    private lateinit var binding: HistoryFragmentBinding

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
    }
    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.SuccessHistoryData -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                adapter.setData(appState.historyData)
            }
            is AppState.Loading -> {
                historyFragmentRecyclerview.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                historyFragmentRecyclerview.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getAllHistory()
                    })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}