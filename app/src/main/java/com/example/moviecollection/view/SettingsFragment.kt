package com.example.moviecollection.view

import IS_ADULT
import PREFERENCES
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviecollection.databinding.SettingsFragmentBinding

class SettingsFragment: Fragment() {
    private lateinit var binding: SettingsFragmentBinding
    private var  isAdultContent: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdultContent()
        with(binding) {
            adultContentSwitch.isChecked = isAdultContent
            adultContentSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                isAdultContent = isChecked
                saveAdultContent()
            }
        }
    }

    private fun initAdultContent() {
        activity?.let {
            isAdultContent = it.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                .getBoolean(IS_ADULT, false)
        }
    }

    private fun saveAdultContent() {
        activity?.let {
            val sharedPreferences = it.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(IS_ADULT, isAdultContent)
            editor.apply()
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}

