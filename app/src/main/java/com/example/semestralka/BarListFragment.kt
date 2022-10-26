package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.semestralka.adapter.BarListItemAdapter
import com.example.semestralka.data.BarDatasource
import com.example.semestralka.databinding.FragmentBarListBinding

class BarListFragment : Fragment() {
    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarListBinding.inflate(inflater, container, false)
        val view = binding.root

        val myDataset = BarDatasource().loadBars(resources.openRawResource(R.raw.pubs))
        val recyclerView = binding.barListRecyclerView
        recyclerView.adapter = BarListItemAdapter(requireContext(), myDataset)

        // Inflate the layout for this fragment
        return view
    }
}