package com.example.semestralka.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralka.BarListFragmentDirections
import com.example.semestralka.R
import com.example.semestralka.databinding.BarListItemBinding
import com.example.semestralka.model.Bar
import com.example.semestralka.viewmodel.BarViewModel

class BarListItemAdapter (
    model: BarViewModel,
    private val onItemClicked: (Bar) -> Unit
): ListAdapter<Bar, BarListItemAdapter.ItemViewHolder>(DiffCallback) {

    private val viewModel = model

    class ItemViewHolder(private var binding: BarListItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        val textView: TextView = view.findViewById(R.id.bar_list_item_name)

        fun bind(bar: Bar) {
            binding.apply {
                barListItemName.text = bar.name
            }
        }

    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            BarListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return viewModel.bars.value?.size ?: 0
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Bar>() {
            override fun areItemsTheSame(oldItem: Bar, newItem: Bar): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Bar, newItem: Bar): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}