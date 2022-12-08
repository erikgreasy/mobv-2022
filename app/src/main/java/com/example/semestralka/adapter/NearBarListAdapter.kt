package com.example.semestralka.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralka.BarDetailFragmentDirections
import com.example.semestralka.data.NearbyBar
import com.example.semestralka.databinding.BarListItemBinding
import com.example.semestralka.databinding.NearBarListItemBinding


class NearBarListAdapter : RecyclerView.Adapter<NearBarListAdapter.NearBarListViewHolder>() {

    inner class NearBarListViewHolder(val binding: NearBarListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<NearbyBar>() {
        override fun areItemsTheSame(
            oldItem: NearbyBar,
            newItem: NearbyBar
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: NearbyBar,
            newItem: NearbyBar
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var bars: List<NearbyBar>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = bars.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearBarListViewHolder {
        return NearBarListViewHolder(
            NearBarListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: NearBarListViewHolder, position: Int) {
        holder.binding.apply {
            val bar = bars[position]
            barListItemName.text = bar.name
//            barListItemUsersCount.text = bar.users
        }

//        holder.binding.barListItemName.setOnClickListener {
//            val action = BarDetailFragmentDirections.actionBarDetail(bars[position].bar_id)
//            holder.itemView.findNavController().navigate(action)
//        }
    }
}