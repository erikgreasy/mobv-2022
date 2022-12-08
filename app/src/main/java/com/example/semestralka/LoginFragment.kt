package com.example.semestralka

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.api.UserRequest
import com.example.semestralka.api.UserResponse
import com.example.semestralka.databinding.ActivityMainBinding
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

//        requireContext() .supportActionBar!!.hide()
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
            binding.usernameInputWrapper.error = null
            binding.passwordInputWrapper.error = null

            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            var error = false

            if(username.trim() == "") {
                binding.usernameInputWrapper.error = "Pole je povinné"
                error = true
            }

            if(password.trim() == "") {
                binding.passwordInputWrapper.error = "Pole je povinné"
                error = true
            }

            if(error) {
                return@setOnClickListener
            }

            authViewModel.login(username, password).observe(requireActivity(), Observer {
                if(it == null) {
                    return@Observer
                }

                if(!it) {
                    binding.passwordInputWrapper.error = "Zadané údaje sú nesprávne"
                    return@Observer
                }
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }
}