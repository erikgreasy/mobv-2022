package com.example.semestralka.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.semestralka.R
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.api.UserRequest
import com.example.semestralka.api.UserResponse
import com.example.semestralka.data.BarDao
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    val loggedUser = MutableLiveData<UserResponse>(null)

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.login(UserRequest(username, password))

            Log.e("GREASY", response.toString())
            Log.e("GREASY", response.body().toString())

            if(response.isSuccessful) {
                val responseBody = response.body()
                Log.e("GREASY", responseBody?.uid.toString())

                if(didUserAuthenticate(responseBody!!)) {
                    loggedUser.value = responseBody!!
                }
            }

        }
    }

    private fun didUserAuthenticate(responseBody: UserResponse): Boolean {
        return responseBody?.uid.toString() != "-1"
    }
}

class AuthViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
