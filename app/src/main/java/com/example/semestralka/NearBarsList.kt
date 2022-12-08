package com.example.semestralka

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semestralka.adapter.BarListItemAdapter
import com.example.semestralka.adapter.NearBarListAdapter
import com.example.semestralka.data.MyLocation
import com.example.semestralka.databinding.FragmentLoginBinding
import com.example.semestralka.databinding.FragmentNearBarsListBinding
import com.example.semestralka.viewmodel.FriendViewModel
import com.example.semestralka.viewmodel.FriendViewModelFactory
import com.example.semestralka.viewmodel.LocateViewModel
import com.example.semestralka.viewmodel.LocateViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

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

//        locateViewModel.loadBars()

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


    fun checkPermissions() {
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
        } else {
            locateViewModel.getLocation()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }
}