package com.example.semestralka.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.semestralka.BarApplication
import com.example.semestralka.R
import com.example.semestralka.databinding.FragmentRegisterBinding
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            (activity?.application as BarApplication)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.loginLink.setOnClickListener {
            it.findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            )
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
                binding.usernameInputWrapper.error = requireContext().getString(R.string.incorrect_data_validation)
                error = true
            }

            if(password.trim() == "") {
                binding.passwordInputWrapper.error = requireContext().getString(R.string.incorrect_data_validation)
                error = true
            }

            if(passwordConfirm.trim() == "") {
                binding.passwordConfirmInputWrapper.error = requireContext().getString(R.string.incorrect_data_validation)
                error = true
            }

            if(error) {
                return@setOnClickListener
            }

            // check password confirmation
            if(password != passwordConfirm) {
                binding.passwordConfirmInputWrapper.error = requireContext().getString(R.string.passwords_dont_match_validation)
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
                    binding.usernameInputWrapper.error = requireContext().getString(R.string.username_exists_validation)
                    return@Observer
                }
            })
        }

        return binding.root
    }
}