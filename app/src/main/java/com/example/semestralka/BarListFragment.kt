package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.semestralka.adapter.BarListItemAdapter
import com.example.semestralka.databinding.FragmentBarListBinding
import com.example.semestralka.viewmodel.BarViewModel
import com.example.semestralka.viewmodel.BarViewModelFactory

const val BASE_URL = "https://data.mongodb-api.com/app/data-fswjp/endpoint/data/v1/"

class BarListFragment : Fragment() {

//    private val viewModel: BarViewModel by activityViewModels {
//        BarViewModelFactory(
//            (activity?.application as BarApplication).database.barDao(),
//            (activity?.application as BarApplication)
//        )
//    }

    private var _binding: FragmentBarListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarListBinding.inflate(inflater, container, false)
        val view = binding.root

        val recyclerView = binding.barListRecyclerView
//        val adapter = BarListItemAdapter(
//            viewModel,
//        {
//            val action =   BarListFragmentDirections.actionBarListFragmentToBarDetailFragment(it.id)
//            this.findNavController().navigate(action)
//         },
//
//        )
//        recyclerView.adapter = adapter

        binding.orderBtn.setOnClickListener {
//            viewModel.order()
//            adapter.notifyDataSetChanged()
        }

//        viewModel.bars.observe(this.viewLifecycleOwner) { bars ->
//            bars.let {
////                adapter.submitList(it)
//            }
//        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Navigation.findNavController(view).navigate(R.id.action_login)
    }

}