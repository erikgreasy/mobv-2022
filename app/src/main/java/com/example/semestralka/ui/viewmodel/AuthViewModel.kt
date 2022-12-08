package com.example.semestralka.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.semestralka.R
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.api.UserRequest
import com.example.semestralka.api.UserResponse
import com.example.semestralka.data.BarDao
import com.example.semestralka.service.PasswordHasher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

class AuthViewModel: ViewModel() {
    val loggedUser = MutableLiveData<UserResponse?>(null)
    val isLoggedIn = MutableLiveData<Boolean>(false)
    val passwordHasher = PasswordHasher()

    fun login(username: String, password: String): LiveData<Boolean?> {
        val success = MutableLiveData<Boolean?>(null)

        viewModelScope.launch {
            try {
                val hashedPassword = passwordHasher.hashPassword(password)
                val response = RetrofitInstance.api.login(UserRequest(username, hashedPassword))

                Log.e("GREASY", response.toString())
                Log.e("GREASY", response.body().toString())

                if(!response.isSuccessful) {
                    success.postValue(false)
                    return@launch
                }
                val responseBody = response.body()

                if(!didUserAuthenticate(responseBody!!)) {
                    success.postValue(false)
                    return@launch
                }

                loggedUser.value = responseBody!!
                isLoggedIn.value = true

                success.postValue(true)
            } catch(e: IOException) {
                e.printStackTrace()
                Log.e("login request", "io exception")
            } catch(e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("login request", "general exception")
            }
        }

        return success
    }

    fun register(username: String, password: String): LiveData<Boolean?> {
        val success = MutableLiveData<Boolean?>(null)

        viewModelScope.launch {
            try {
                val hashedPassword = passwordHasher.hashPassword(password)
                val response = RetrofitInstance.api.register(UserRequest(username, hashedPassword))

                if(!response.isSuccessful) {
                    success.postValue(false)
                    return@launch
                }

                val responseBody = response.body()

                if(!didUserAuthenticate(responseBody!!)) {
                    success.postValue(false)
                    return@launch
                }

                Log.e("PREBEHLO USPESNE", responseBody.toString())
                loggedUser.value = responseBody!!
                isLoggedIn.value = true

                success.postValue(true)
            } catch(e: IOException) {
                e.printStackTrace()
                Log.e("register request", "io exception")
            } catch(e: Exception) {
                e.printStackTrace()
                Log.e("register request", "general exception")
            }
        }

        return success
    }

    fun logout() {
        loggedUser.value = null
        isLoggedIn.value = false
    }

    private fun didUserAuthenticate(responseBody: UserResponse): Boolean {
        Log.e("did authenticate", (responseBody?.uid.toString() != "-1").toString())
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
