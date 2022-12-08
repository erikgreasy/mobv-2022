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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.semestralka.R
import com.example.semestralka.data.Bar
import com.example.semestralka.databinding.BarListItemBinding
import com.example.semestralka.ui.fragments.bars.BarDetailFragmentDirections
import com.example.semestralka.ui.viewmodel.BarViewModel

class BarListItemAdapter : RecyclerView.Adapter<BarListItemAdapter.BarListViewHolder>() {

    inner class BarListViewHolder(val binding: BarListItemBinding) : ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<com.example.semestralka.api.Bar>() {
        override fun areItemsTheSame(
            oldItem: com.example.semestralka.api.Bar,
            newItem: com.example.semestralka.api.Bar
        ): Boolean {
            return oldItem.bar_id == newItem.bar_id
        }
        override fun areContentsTheSame(
            oldItem: com.example.semestralka.api.Bar,
            newItem: com.example.semestralka.api.Bar
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var bars: List<com.example.semestralka.api.Bar>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = bars.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarListViewHolder {
        return BarListViewHolder(BarListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BarListViewHolder, position: Int) {
        holder.binding.apply {
            val bar = bars[position]
            barListItemName.text = bar.bar_name
            barListItemUsersCount.text = bar.users
        }

        holder.binding.barListItemName.setOnClickListener {
            val action = BarDetailFragmentDirections.actionBarDetail(bars[position].bar_id)
            holder.itemView.findNavController().navigate(action)
        }
    }
}