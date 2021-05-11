package com.example.moviecollection.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecollection.databinding.FragmentItemBinding
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.interfaces.OnItemViewClickListener

class HorizontalFragmentAdapter(private var itemClickListener: OnItemViewClickListener?)
    : RecyclerView.Adapter<HorizontalFragmentAdapter.ItemViewHolder>() {
    private lateinit var binding: FragmentItemBinding
    private var moviesData: List<Movie> = listOf()

    fun removeListener() {
        itemClickListener = null
    }

    fun setMovies(data: List<Movie>) {
        moviesData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): ItemViewHolder {
        binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(moviesData[position])
    }

    override fun getItemCount() = moviesData.size

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) = with(binding) {
            itemTitle.text = movie.title
            itemYear.text = movie.yearOfRelease.toString()
            root.setOnClickListener {
                itemClickListener?.onItemViewClick(movie)
            }
        }

    }
}