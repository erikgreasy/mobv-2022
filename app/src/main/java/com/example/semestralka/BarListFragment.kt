package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.semestralka.adapter.BarListItemAdapter
import com.example.semestralka.data.BarDatasource
import com.example.semestralka.databinding.FragmentBarListBinding
import com.example.semestralka.model.Bar

class BarListFragment : Fragment() {
    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataset: MutableList<Bar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataset = BarDatasource().loadBars(resources.openRawResource(R.raw.pubs))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarListBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = binding.barListRecyclerView
        val adapter = BarListItemAdapter(requireContext(), dataset)
        recyclerView.adapter = adapter

        binding.orderBtn.setOnClickListener {
            orderBars()
            adapter.setDataset(this.dataset)
            adapter.notifyDataSetChanged()
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun orderBars() {
        dataset = dataset.sortedWith(compareBy({it.name})).toMutableList()
    }
}