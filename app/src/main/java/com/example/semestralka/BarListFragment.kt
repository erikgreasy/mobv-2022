package com.example.semestralka

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.semestralka.adapter.BarListItemAdapter
import com.example.semestralka.data.BarDatasource
import com.example.semestralka.data.PubsData
import com.example.semestralka.databinding.FragmentBarListBinding
import com.example.semestralka.model.Bar
import com.example.semestralka.viewmodel.BarViewModel
import com.example.semestralka.viewmodel.BarViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://data.mongodb-api.com/app/data-fswjp/endpoint/data/v1/"

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
        val adapter = BarListItemAdapter(
            viewModel,
        {
            val action =   BarListFragmentDirections.actionBarListFragmentToBarDetailFragment(it.name)
            this.findNavController().navigate(action)
         },

        )
        recyclerView.adapter = adapter

        binding.orderBtn.setOnClickListener {
            viewModel.order()
//            adapter.notifyDataSetChanged()
        }

        viewModel.bars.observe(this.viewLifecycleOwner) { bars ->
            bars.let {
                adapter.submitList(it)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}