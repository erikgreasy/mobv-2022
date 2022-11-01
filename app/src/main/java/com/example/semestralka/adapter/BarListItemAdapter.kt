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
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralka.BarListFragmentDirections
import com.example.semestralka.R
import com.example.semestralka.model.Bar
import com.example.semestralka.viewmodel.BarViewModel

class BarListItemAdapter (
    private val context: Context,
    private var dataset: List<String>
): RecyclerView.Adapter<BarListItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.bar_list_item_name)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.bar_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item
        holder.textView.setOnClickListener {
            val action = BarListFragmentDirections.actionBarListFragmentToBarDetailFragment(
                name = item
            )

            holder.view.findNavController().navigate(action)
        }
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}