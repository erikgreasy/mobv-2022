package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semestralka.databinding.FragmentFriendsAddedByMeBinding
import com.example.semestralka.databinding.FragmentFriendsBinding
import com.example.semestralka.ui.adapter.FriendsAddedByMeAdapter
import com.example.semestralka.ui.adapter.FriendsListAdapter
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory
import com.example.semestralka.ui.viewmodel.FriendViewModel
import com.example.semestralka.ui.viewmodel.FriendViewModelFactory

class FriendsAddedByMeFragment : Fragment() {
    private var _binding: FragmentFriendsAddedByMeBinding? = null
    private val binding get() = _binding!!
    private lateinit var friendsAddedBeMeAdapter: FriendsAddedByMeAdapter

    private val friendViewModel: FriendViewModel by activityViewModels {
        FriendViewModelFactory(
            authViewModel,
            (activity?.application as BarApplication)
        )
    }

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            (activity?.application as BarApplication)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendsAddedByMeBinding.inflate(inflater, container, false)

        setupRecyclerView()

        friendsAddedBeMeAdapter.friends = friendViewModel.friendsAddedByMe.value!!

        friendViewModel.friendsAddedByMe.observe(viewLifecycleOwner, Observer {
            friendsAddedBeMeAdapter.friends = it
        })

        binding.friendsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_friends)
        }

        return binding.root
    }

    private fun setupRecyclerView() = binding.friendsAddedByMeRecyclerView.apply {
        friendsAddedBeMeAdapter = FriendsAddedByMeAdapter(friendViewModel)
        adapter = friendsAddedBeMeAdapter
        layoutManager = LinearLayoutManager(this.context)
    }
}