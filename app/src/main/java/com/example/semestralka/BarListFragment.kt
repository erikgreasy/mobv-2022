package com.example.semestralka

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.semestralka.adapter.BarListItemAdapter
import com.example.semestralka.data.BarDatasource
import com.example.semestralka.databinding.FragmentBarListBinding
import com.example.semestralka.model.Bar
import com.example.semestralka.viewmodel.BarViewModel
import com.example.semestralka.viewmodel.BarViewModelFactory

class BarListFragment : Fragment() {

    private val viewModel: BarViewModel by activityViewModels {
        BarViewModelFactory()
    }

    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarListBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = binding.barListRecyclerView
        val adapter = BarListItemAdapter(requireContext(), viewModel.bars)
        recyclerView.adapter = adapter

        binding.orderBtn.setOnClickListener {
            viewModel.order()
            adapter.notifyDataSetChanged()
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}