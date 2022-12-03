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
import com.example.semestralka.api.Bar
import com.example.semestralka.databinding.FragmentBarDetailBinding
import com.example.semestralka.viewmodel.AuthViewModel
import com.example.semestralka.viewmodel.AuthViewModelFactory
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

    private val barViewModel: BarViewModel by activityViewModels {
        BarViewModelFactory(
            authViewModel,
            (activity?.application as BarApplication)
        )
    }

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
    }

    private var _binding: FragmentBarDetailBinding? = null
    private val binding get() = _binding!!


    private fun bind(bar: Bar) {
        binding.apply {
            barName.text = bar.bar_name

            deleteBtn.setOnClickListener {
//
//                val action =   BarDetailFragmentDirections.actionBarDetailFragmentToBarListFragment()
//                findNavController().navigate(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barId = navigationArgs.id

        val bar = barViewModel.getBar(barId)

        if(bar != null) {
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