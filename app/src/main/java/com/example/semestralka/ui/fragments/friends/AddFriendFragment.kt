package com.example.semestralka.ui.fragments.friends

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.semestralka.BarApplication
import com.example.semestralka.R
import com.example.semestralka.databinding.FragmentAddFriendBinding
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory
import com.example.semestralka.ui.viewmodel.FriendViewModel
import com.example.semestralka.ui.viewmodel.FriendViewModelFactory

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
        AuthViewModelFactory(
            (activity?.application as BarApplication)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            val friendUsername = binding.usernameInput.text.toString()
            Log.e("ADD FRIEND", friendUsername)

            binding.usernameInputWrapper.error = null

            friendViewModel.addFriend(friendUsername).observe(viewLifecycleOwner, Observer {
                if(it == null) {
                    return@Observer
                }

                if(!it) {
                    binding.usernameInputWrapper.error = requireContext().getString(R.string.add_friend_cannot_add)
                    return@Observer
                }

                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.add_friend_added),
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_friends)
            })
        }

        return binding.root
    }
}