package com.example.semestralka

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.api.UserRequest
import com.example.semestralka.api.UserResponse
import com.example.semestralka.databinding.FragmentLoginBinding
import com.example.semestralka.viewmodel.AuthViewModel
import com.example.semestralka.viewmodel.AuthViewModelFactory
import com.example.semestralka.viewmodel.BarViewModel
import com.example.semestralka.viewmodel.BarViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.registerLink.setOnClickListener {
            it.findNavController().navigate(R.id.action_register)
        }

        binding.submitButton.setOnClickListener {
            binding.progressBar.isVisible = true

            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()

            authViewModel.login(username, password)

            Log.e("SUCCESS GREASY TRY", authViewModel.loggedUser.toString())

            // TODO: refactor this because now the process in couroutine doesnt work correclty showing this progressbar
            binding.progressBar.isVisible = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.loggedUser.observe(viewLifecycleOwner, { newUser ->
            if(newUser != null) {
                findNavController().navigate(R.id.action_bar_list)
            }
        })
    }



}