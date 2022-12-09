package com.example.semestralka.ui.viewmodel

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
    val friendsAddedByMe = MutableLiveData<List<User>>(listOf())
    val loading = MutableLiveData<Boolean>(false)

    init {
        loadFriends()
        loadFriendsAddedByMe()
    }

    fun loadFriends() {
        Log.e("FRINED VIEW MODEL", "LOADING FRIENDS")

        viewModelScope.launch {
            val response = RetrofitInstance.getInstance(application).api.getFriends()

            Log.e("GREASY", response.toString())

            if(response.isSuccessful) {
                Log.e("GREASY", response.body().toString())

                friends.value = response.body()!!
            }

            Log.e("FRIEND VIEW MODEL", friends.value.toString())
        }
    }

    fun loadFriendsAddedByMe() {
        Log.e("FRINED VIEW MODEL", "LOADING FRIENDS added by me")

        viewModelScope.launch {
            val response = RetrofitInstance.getInstance(application).api.getFriendsAddedByMe()

            Log.e("get friends added by me", response.toString())

            if(response.isSuccessful) {
                Log.e("GREASY", response.body().toString())

                friendsAddedByMe.value = response.body()!!
            }

            Log.e("frnds added by me res", friendsAddedByMe.value.toString())
        }
    }

    fun deleteFriend(friendUsername: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.getInstance(application).api.deleteFriend(
                AddFriendRequest(friendUsername)
            )

            if(!response.isSuccessful) {
                return@launch
            }

            loadFriendsAddedByMe()
        }
    }

    fun addFriend(friendUsername: String): LiveData<Boolean?> {
        val successful = MutableLiveData<Boolean?>(null)

        viewModelScope.launch {
//            loading.value = true

            val response = RetrofitInstance.getInstance(application).api.addFriend(
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
