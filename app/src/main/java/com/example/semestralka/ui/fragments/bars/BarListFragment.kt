package com.example.semestralka.ui.fragments.bars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semestralka.BarApplication
import com.example.semestralka.ui.adapter.BarListItemAdapter
import com.example.semestralka.databinding.FragmentBarListBinding
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory
import com.example.semestralka.ui.viewmodel.BarViewModel
import com.example.semestralka.ui.viewmodel.BarViewModelFactory

class BarListFragment : Fragment() {

    private val barViewModel: BarViewModel by activityViewModels {
        BarViewModelFactory(
            authViewModel,
            (activity?.application as BarApplication)
        )
    }

    private lateinit var barListItemAdapter: BarListItemAdapter

    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarListBinding.inflate(inflater, container, false)

        setupRecyclerView()


        barListItemAdapter.bars = barViewModel.bars.value!!

        barViewModel.bars.observe(viewLifecycleOwner, Observer {
            barListItemAdapter.bars = it
        })

        binding.swiperefresh.setOnRefreshListener {
            barViewModel.loadBars()
            binding.swiperefresh.isRefreshing = false
        }

        return binding.root
    }

    private fun setupRecyclerView() = binding.barListRecyclerView.apply {
        barListItemAdapter = BarListItemAdapter()
        adapter = barListItemAdapter
        layoutManager = LinearLayoutManager(this.context)
    }

}