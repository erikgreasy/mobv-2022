package com.example.semestralka.ui.fragments.nearbars

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semestralka.ui.adapter.NearBarListAdapter
import com.example.semestralka.databinding.FragmentNearBarsListBinding
import com.example.semestralka.ui.viewmodel.LocateViewModel
import com.example.semestralka.ui.viewmodel.LocateViewModelFactory

class NearBarsList : Fragment() {
    private var _binding: FragmentNearBarsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var nearBarListAdapter: NearBarListAdapter

    private val locateViewModel: LocateViewModel by activityViewModels {
        LocateViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNearBarsListBinding.inflate(inflater, container, false)

        setupRecyclerView()


        checkPermissions()


        locateViewModel.bars.observe(viewLifecycleOwner, Observer {
            nearBarListAdapter.bars = it
        })

        Log.e("ASD", locateViewModel.bars.value.toString())

        return binding.root
    }

    private fun setupRecyclerView() = binding.nearBarListRecyclerView.apply {
        nearBarListAdapter = NearBarListAdapter()
        nearBarListAdapter.locateViewModel = locateViewModel
        adapter = nearBarListAdapter
        layoutManager = LinearLayoutManager(this.context)
    }


    fun checkPermissions() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        } else {
            locateViewModel.getLocation()
        }
    }
}