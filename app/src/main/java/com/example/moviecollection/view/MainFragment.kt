package com.example.moviecollection.view

import androidx.fragment.app.Fragment
import com.example.moviecollection.databinding.MainFragmentBinding

class MainFragment : Fragment(){
    private lateinit var binding: MainFragmentBinding

    companion object {
        fun newInstance() =
                MainFragment()
    }
}