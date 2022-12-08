package com.example.semestralka.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.semestralka.data.NearbyBar
import kotlinx.coroutines.launch


class LocateViewModel(): ViewModel() {
    val loading = MutableLiveData(false)

    val bars = MutableLiveData<List<NearbyBar>>(listOf())

    init {
        loadBars()
    }

    fun loadBars() {
        bars.value = listOf(
            NearbyBar("123", "Nazov1", "Restika"),
            NearbyBar("1234", "Nazov2", "Restika"),
            NearbyBar("125", "Nazov3", "Restika"),
        )
    }
}

class LocateViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocateViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
