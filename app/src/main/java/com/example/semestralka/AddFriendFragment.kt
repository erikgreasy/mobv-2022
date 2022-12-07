package com.example.semestralka

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.semestralka.databinding.FragmentAddFriendBinding
import com.example.semestralka.viewmodel.AuthViewModel
import com.example.semestralka.viewmodel.AuthViewModelFactory
import com.example.semestralka.viewmodel.FriendViewModel
import com.example.semestralka.viewmodel.FriendViewModelFactory

class AddFriendFragment : Fragment() {
    private var _binding: FragmentAddFriendBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            val friendUsername = binding.usernameInput.text.toString()
            Log.e("ADD FRIEND", friendUsername)

            // TODO make request to add friend
            friendViewModel.addFriend(friendUsername)

            findNavController().navigate(R.id.action_friends)
        }

        return binding.root
    }
}