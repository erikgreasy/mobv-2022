package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.semestralka.databinding.FragmentBarDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [BarDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarDetailFragment : Fragment() {
    private var _binding: FragmentBarDetailBinding? = null
    private val binding get() = _binding!!
    private var barName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            barName = it.getString("name").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.barName.text = barName.toString()

        binding.deleteBtn.setOnClickListener {
            val action = BarDetailFragmentDirections.actionBarDetailFragmentToBarListFragment()

            findNavController().navigate(action)
        }

        return view
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_bar_detail, container, false)
    }
}