package com.example.moviecollection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecollection.databinding.FragmentMainRecyclerItemBinding
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.interfaces.OnItemViewClickListener

class MainFragmentAdapter(private var itemClickListener: OnItemViewClickListener?)
    : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private lateinit var binding: FragmentMainRecyclerItemBinding
    private var moviesData: List<Movie> = listOf()

    fun removeListener() {
        itemClickListener = null
    }

    fun setMovies(data: List<Movie>) {
        moviesData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): MainViewHolder {
        binding = FragmentMainRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(moviesData[position])
    }

    override fun getItemCount(): Int {
        return moviesData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) = with(binding) {
            mainFragmentRecyclerItemTitle.text = movie.title
            mainFragmentRecyclerItemGenre.text = movie.genre
            mainFragmentRecyclerItemYear.text = movie.yearOfRelease.toString()
            root.setOnClickListener {
                itemClickListener?.onItemViewClick(movie)
            }
        }

    }
}