package com.example.semestralka.ui.fragments.bars

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.semestralka.BarApplication
import com.example.semestralka.api.Bar
import com.example.semestralka.data.NearbyBar
import com.example.semestralka.databinding.FragmentBarDetailBinding
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory
import com.example.semestralka.ui.viewmodel.BarViewModel
import com.example.semestralka.ui.viewmodel.BarViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [BarDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarDetailFragment : Fragment() {
    private val navigationArgs: BarDetailFragmentArgs by navArgs()
    lateinit var bar: NearbyBar
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


    private fun bind(bar: NearbyBar) {
        binding.apply {
            barName.text = bar.name

            binding.showOnMap.setOnClickListener {
                val mapIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0,?q=" +
                            "${bar.lat ?: 0}," +
                            "${bar.lon ?: 0}" +
                            "(${bar.name ?: ""}")
                )
                startActivity(mapIntent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barId = navigationArgs.id

        barViewModel.getBar(barId)

        barViewModel.barDetail.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                bar = it
                bind(bar)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}