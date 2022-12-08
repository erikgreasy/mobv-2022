package com.example.semestralka.ui.adapter

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
import com.example.semestralka.data.BarDetailListItem
import com.example.semestralka.databinding.BarDetailListItemBinding
import com.example.semestralka.databinding.BarListItemBinding
import com.example.semestralka.ui.fragments.bars.BarDetailFragmentDirections
import com.example.semestralka.ui.viewmodel.BarViewModel

class BarDetailListAdapter : RecyclerView.Adapter<BarDetailListAdapter.BarDetailListViewHolder>() {

    inner class BarDetailListViewHolder(val binding: BarDetailListItemBinding) : ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<BarDetailListItem>() {
        override fun areItemsTheSame(
            oldItem: BarDetailListItem,
            newItem: BarDetailListItem
        ): Boolean {
            return oldItem.value == newItem.value
        }
        override fun areContentsTheSame(
            oldItem: BarDetailListItem,
            newItem: BarDetailListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var data: List<BarDetailListItem>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarDetailListViewHolder {
        return BarDetailListViewHolder(BarDetailListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BarDetailListViewHolder, position: Int) {
        holder.binding.apply {
            val item = data[position]
            barDetailListName.text = item.key
            barDetailListValue.text = item.value
        }
    }
}