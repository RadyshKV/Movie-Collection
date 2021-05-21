package com.example.moviecollection.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecollection.R
import com.example.moviecollection.databinding.CategoriesFragmentBinding
import com.example.moviecollection.model.entities.Movie
import com.example.moviecollection.model.interfaces.OnItemViewClickListener
import com.example.moviecollection.view.DetailsFragment
import com.example.moviecollection.view.MainFragment


class VerticalFragmentAdapter(private var mainFragment: MainFragment)
    : RecyclerView.Adapter<VerticalFragmentAdapter.MainViewHolder>() {
    private lateinit var binding: CategoriesFragmentBinding
    private var movies: List<Movie> = listOf()
    private var categories: MutableCollection<String> = mutableSetOf()


    fun setMovies(data: List<Movie>) {
        movies = data
    }

    fun initCategories() {
        for (movie in movies) {
            movie.genre.let {
                categories.add(it[0])
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): MainViewHolder {
        binding = CategoriesFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(categories.elementAt(position))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @Suppress("NAME_SHADOWING")
    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var horizontalRecyclerView: RecyclerView
        private lateinit var horizontalAdapter: HorizontalFragmentAdapter


        fun bind(categorie: String) = with(binding) {
            horizontalFragmentTitle.text = categorie
            horizontalRecyclerView = horizontalFragmentRecyclerView
            horizontalAdapter = HorizontalFragmentAdapter(
                    object : OnItemViewClickListener {
                        override fun onItemViewClick(movie: Movie) {
                            mainFragment.activity?.supportFragmentManager?.let { manager ->
                                val bundle = Bundle().apply {
                                    putParcelable(DetailsFragment.BUNDLE_EXTRA, movie)
                                }
                                manager.beginTransaction()
                                        .add(R.id.container, DetailsFragment.newInstance(bundle))
                                        .addToBackStack("")
                                        .commitAllowingStateLoss()
                            }
                        }
                    }).apply {
                setMovies(movies.filter { it.genre.contains(categorie) == true })
            }
            horizontalRecyclerView.adapter = horizontalAdapter
        }
    }
}
