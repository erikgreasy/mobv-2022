package com.example.semestralka.ui.fragments.auth

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.semestralka.BarApplication
import com.example.semestralka.R
import com.example.semestralka.databinding.FragmentLoginBinding
import com.example.semestralka.ui.viewmodel.AuthViewModel
import com.example.semestralka.ui.viewmodel.AuthViewModelFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory(
            (activity?.application as BarApplication)
        )
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
                binding.usernameInputWrapper.error = requireContext().getString(R.string.required_validation)
                error = true
            }

            if(password.trim() == "") {
                binding.passwordInputWrapper.error = requireContext().getString(R.string.required_validation)
                error = true
            }

            if(error) {
                return@setOnClickListener
            }

            binding.progressBar.isVisible = true

            authViewModel.login(username, password).observe(requireActivity(), Observer {
                if(it == null) {
                    return@Observer
                }

                if(!it) {
                    binding.passwordInputWrapper.error = requireContext().getString(R.string.incorrect_data_validation)
                }

                binding.progressBar.isVisible = false
            })
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }
}