package com.example.semestralka.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.semestralka.R
import com.example.semestralka.databinding.FragmentRegisterBinding
import com.example.semestralka.viewmodel.AuthViewModel
import com.example.semestralka.viewmodel.AuthViewModelFactory

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.loginLink.setOnClickListener {
            it.findNavController().navigate(R.id.action_login)
        }

        binding.submitButton.setOnClickListener {
            val username = binding.nameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val passwordConfirm = binding.passwordConfirmInput.text.toString()

            binding.usernameInputWrapper.error = null
            binding.passwordInputWrapper.error = null
            binding.passwordConfirmInputWrapper.error = null

            var error = false

            if(username.trim() == "") {
                binding.usernameInputWrapper.error = "Pole je povinné"
                error = true
            }

            if(password.trim() == "") {
                binding.passwordInputWrapper.error = "Pole je povinné"
                error = true
            }

            if(passwordConfirm.trim() == "") {
                binding.passwordConfirmInputWrapper.error = "Pole je povinné"
                error = true
            }

            if(error) {
                return@setOnClickListener
            }

            // check password confirmation
            if(password != passwordConfirm) {
                binding.passwordConfirmInputWrapper.error = "Heslo sa nezhoduje"
                error = true
            }

            if(error) return@setOnClickListener

            // try to register user

            authViewModel.register(
                username,
                password
            ).observe(requireActivity(), Observer {
                if(it == null) {
                    return@Observer
                }

                if(!it) {
                    binding.usernameInputWrapper.error = "Zadané meno už evidujeme"
                    return@Observer
                }
            })
        }

        return binding.root
    }
}