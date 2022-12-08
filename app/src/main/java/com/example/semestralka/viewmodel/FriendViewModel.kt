package com.example.semestralka.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.semestralka.api.AddFriendRequest
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.api.User
import kotlinx.coroutines.launch

class FriendViewModel(val authViewModel: AuthViewModel, val application: Application): ViewModel() {
//    val bars = MutableLiveData<MutableList<Bar>>()
    val friends = MutableLiveData<List<User>>(listOf())
    val loading = MutableLiveData<Boolean>(false)

    init {
        loadFriends()
    }

    fun loadFriends() {
        Log.e("FRINED VIEW MODEL", "LOADING FRIENDS")

        viewModelScope.launch {
            val response = RetrofitInstance.api.getFriends(
                authViewModel.loggedUser.value?.uid!!,
                "Bearer " + authViewModel.loggedUser.value?.access!!
            )

            Log.e("GREASY", response.toString())

            if(response.isSuccessful) {
                Log.e("GREASY", response.body().toString())

                friends.value = response.body()!!
            }

            Log.e("FRIEND VIEW MODEL", friends.value.toString())
        }
    }

    fun addFriend(friendUsername: String): LiveData<Boolean?> {
        val successful = MutableLiveData<Boolean?>(null)

        viewModelScope.launch {
//            loading.value = true

            val response = RetrofitInstance.api.addFriend(
                authViewModel.loggedUser.value?.uid!!,
                "Bearer " + authViewModel.loggedUser.value?.access!!,
                AddFriendRequest(friendUsername)
            )

            if(!response.isSuccessful) {
                successful.postValue(false)
                return@launch
//                Toast.makeText(application.applicationContext, "Nepodarilo sa pridať priateľa. Zadali ste správne meno?", Toast.LENGTH_SHORT).show()

            }

            successful.postValue(true)
//            loading.value = false

            Log.e("FRIEND VIEW MODEL", friends.value.toString())
        }

        return successful
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
