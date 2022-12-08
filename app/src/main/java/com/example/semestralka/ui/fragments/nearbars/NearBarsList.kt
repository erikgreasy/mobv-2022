package com.example.semestralka.ui.fragments.nearbars

import android.Manifest
import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semestralka.ui.adapter.NearBarListAdapter
import com.example.semestralka.databinding.FragmentNearBarsListBinding
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory
import com.example.semestralka.ui.viewmodel.LocateViewModel
import com.example.semestralka.ui.viewmodel.LocateViewModelFactory

class NearBarsList : Fragment() {
    private var _binding: FragmentNearBarsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var nearBarListAdapter: NearBarListAdapter

    private val locateViewModel: LocateViewModel by activityViewModels {
        LocateViewModelFactory(requireActivity(), authViewModel)
    }

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
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

        locateViewModel.nearBarToCheckin.observe(viewLifecycleOwner, Observer {
            binding.choosenBarName.text = it?.name
        })

        binding.checkinBtn.setOnClickListener {
            Log.e("checkin to", locateViewModel.nearBarToCheckin?.value.toString())
            locateViewModel.checkinToBar().observe(viewLifecycleOwner, Observer {
                if(it == null) {
                    return@Observer
                }

                if(!it) {
                    return@Observer
                }

                binding.animationView.isVisible = true
                binding.animationView.playAnimation()
                binding.animationView.addAnimatorListener(object: Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }
                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        binding.animationView.isVisible = false
                    }
                })
            })
        }


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