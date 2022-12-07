package com.example.semestralka.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.api.User
import kotlinx.coroutines.launch

class FriendViewModel(authViewModel: AuthViewModel, application: Application): ViewModel() {
//    val bars = MutableLiveData<MutableList<Bar>>()
    val friends = MutableLiveData<List<User>>(listOf())


    init {
        loadFriends(authViewModel)
    }

    fun loadFriends(authViewModel: AuthViewModel) {
        Log.e("FRINED VIEW MODEL", "LOADING FRIENDS")

        viewModelScope.launch {
            val response = RetrofitInstance.api.getFriends(
                authViewModel.loggedUser.value?.uid!!,
                "Bearer " + authViewModel.loggedUser.value?.access!!
            )

            Log.e("GREASY", response.toString())

            if(response.isSuccessful) {
                friends.value = response.body()!!
            }

            Log.e("FRIEND VIEW MODEL", friends.value.toString())
        }
    }
}

class FriendViewModelFactory(private val authViewModel: AuthViewModel, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(authViewModel, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
