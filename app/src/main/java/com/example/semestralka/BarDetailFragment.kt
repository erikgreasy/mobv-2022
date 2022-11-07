package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.semestralka.data.Bar
import com.example.semestralka.databinding.FragmentBarDetailBinding
import com.example.semestralka.viewmodel.BarViewModel
import com.example.semestralka.viewmodel.BarViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [BarDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarDetailFragment : Fragment() {
    private val navigationArgs: BarDetailFragmentArgs by navArgs()
    lateinit var bar: Bar
//    private var barName: String = ""

    private val viewModel: BarViewModel by activityViewModels {
        BarViewModelFactory(
            (activity?.application as BarApplication).database.barDao()
        )
    }

    private var _binding: FragmentBarDetailBinding? = null
    private val binding get() = _binding!!


    private fun bind(bar: Bar) {
        binding.apply {
            barName.text = bar.name

            deleteBtn.setOnClickListener {
                viewModel.deleteBar(bar)

                val action =   BarDetailFragmentDirections.actionBarDetailFragmentToBarListFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barId = navigationArgs.id

        viewModel.retrieveItem(barId).observe(this.viewLifecycleOwner) { selectedBar ->
            bar = selectedBar
            bind(bar)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}