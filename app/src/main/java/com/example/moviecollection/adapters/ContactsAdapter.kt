package com.example.moviecollection.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecollection.databinding.ItemListContactBinding
import com.example.moviecollection.model.entities.Contact

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.RecyclerItemViewHolder>() {
    private var data: MutableList<Contact> = mutableListOf()

    fun addData(data: Contact) {
        this.data.add(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            ItemListContactBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(private val binding: ItemListContactBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Contact) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                val cont = "${data.name} ${data.phone}"
                contactName.text = cont
            }
        }
    }
}