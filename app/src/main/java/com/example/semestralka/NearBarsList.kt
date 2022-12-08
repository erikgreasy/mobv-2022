package com.example.semestralka

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semestralka.adapter.BarListItemAdapter
import com.example.semestralka.adapter.NearBarListAdapter
import com.example.semestralka.databinding.FragmentLoginBinding
import com.example.semestralka.databinding.FragmentNearBarsListBinding
import com.example.semestralka.viewmodel.FriendViewModel
import com.example.semestralka.viewmodel.FriendViewModelFactory
import com.example.semestralka.viewmodel.LocateViewModel
import com.example.semestralka.viewmodel.LocateViewModelFactory

class NearBarsList : Fragment() {
    private var _binding: FragmentNearBarsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var nearBarListAdapter: NearBarListAdapter

    private val locateViewModel: LocateViewModel by activityViewModels {
        LocateViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearBarsListBinding.inflate(inflater, container, false)

        setupRecyclerView()

        locateViewModel.bars.observe(viewLifecycleOwner, Observer {
            nearBarListAdapter.bars = it
        })

        Log.e("ASD", locateViewModel.bars.value.toString())

        return binding.root
    }

    private fun setupRecyclerView() = binding.nearBarListRecyclerView.apply {
        nearBarListAdapter = NearBarListAdapter()
        adapter = nearBarListAdapter
        layoutManager = LinearLayoutManager(this.context)
    }
}