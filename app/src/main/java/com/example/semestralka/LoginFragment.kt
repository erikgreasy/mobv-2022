package com.example.semestralka

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.api.UserRequest
import com.example.semestralka.api.UserResponse
import com.example.semestralka.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
            lifecycleScope.launch {
                binding.progressBar.isVisible = true

                val username = binding.usernameInput.text.toString()
                val password = binding.passwordInput.text.toString()

                val response = RetrofitInstance.api.login(UserRequest(username, password))

                Log.e("GREASY", response.toString())
                Log.e("GREASY", response.body().toString())

                if(response.isSuccessful) {
                    val responseBody = response.body()
                    Log.e("GREASY", responseBody?.uid.toString())

                    // user entered correct credentials
                    if(didUserAuthenticate(responseBody!!)) {
                        findNavController().navigate(R.id.action_bar_list)
                    }
                }

                binding.progressBar.isVisible = false
            }
        }

        return binding.root
    }

    private fun didUserAuthenticate(responseBody: UserResponse): Boolean {
        return responseBody?.uid.toString() != "-1"
    }

}