package com.example.semestralka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.semestralka.databinding.FragmentLoginBinding
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

            // check password confirmation

            // try to register user
            authViewModel.register(
                username,
                password
            )
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