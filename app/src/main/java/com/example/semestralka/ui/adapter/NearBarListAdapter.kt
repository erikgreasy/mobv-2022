package com.example.semestralka.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralka.data.MyLocation
import com.example.semestralka.data.NearbyBar
import com.example.semestralka.databinding.BarListItemBinding
import com.example.semestralka.databinding.NearBarListItemBinding
import com.example.semestralka.service.DistanceCounter
import com.example.semestralka.ui.viewmodel.LocateViewModel
import com.example.semestralka.ui.viewmodel.LocateViewModelFactory


class NearBarListAdapter() : RecyclerView.Adapter<NearBarListAdapter.NearBarListViewHolder>() {

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

    lateinit var locateViewModel: LocateViewModel

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

            val distanceCounterService = DistanceCounter()

            barListItemName.text = bar.name

            val barDistance = distanceCounterService.countDistance(
                locateViewModel.location!!,
                MyLocation(bar.lat, bar.lon)
            )

            nearBarListItem.setOnClickListener {
                locateViewModel.nearBarToCheckin.value = bar
            }

            barListItemDistance.text = "%.2f m".format(barDistance)
        }
    }
}