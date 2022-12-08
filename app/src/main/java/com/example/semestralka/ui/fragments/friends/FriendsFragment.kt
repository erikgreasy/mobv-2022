package com.example.semestralka.ui.fragments.friends

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.semestralka.BarApplication
import com.example.semestralka.R
import com.example.semestralka.adapter.FriendsListAdapter
import com.example.semestralka.databinding.FragmentFriendsBinding
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory
import com.example.semestralka.ui.viewmodel.FriendViewModel
import com.example.semestralka.ui.viewmodel.FriendViewModelFactory
import com.example.semestralka.viewmodel.*

class FriendsFragment : Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private lateinit var friendItemAdapter: FriendsListAdapter

    private val friendViewModel: FriendViewModel by activityViewModels {
        FriendViewModelFactory(
            authViewModel,
            (activity?.application as BarApplication)
        )
    }

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)

        setupRecyclerView()

        friendItemAdapter.friends = friendViewModel.friends.value!!

        friendViewModel.friends.observe(viewLifecycleOwner, Observer {
            friendItemAdapter.friends = it
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_add_friend)
        }

        return binding.root
    }

    private fun setupRecyclerView() = binding.friendsListRecyclerView.apply {
        friendItemAdapter = FriendsListAdapter()
        adapter = friendItemAdapter
        layoutManager = LinearLayoutManager(this.context)
    }
}