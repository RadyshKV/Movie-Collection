package com.example.moviecollection.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecollection.databinding.ItemListHistoryBinding
import com.example.moviecollection.model.database.HistoryEntity

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {
    private var data: List<HistoryEntity> = arrayListOf()

    fun setData(data: List<HistoryEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            ItemListHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(private val binding: ItemListHistoryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HistoryEntity) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                title.text = data.title
                date.text = data.date
            }
        }
    }
}