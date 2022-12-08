package com.example.semestralka.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.semestralka.api.BarDetailItemResponse
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.data.NearbyBar
import kotlinx.coroutines.launch


class LocateViewModel(): ViewModel() {
    val loading = MutableLiveData(false)

    val bars = MutableLiveData<List<NearbyBar>>(listOf())

    init {
        loadBars()
    }

    fun loadBars() {
//        bars.value = listOf(
//            NearbyBar("123", "Nazov1", "Restika"),
//            NearbyBar("1234", "Nazov2", "Restika"),
//            NearbyBar("125", "Nazov3", "Restika"),
//        )
        viewModelScope.launch {


            val lat = "48.1591397"
            val lon = "17.0634386"
            val q = "[out:json];node(around:250,$lat,$lon);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;"

            val response = RetrofitInstance.api.barNearby(q)


            if(response.isSuccessful) {
                bars.value = response.body()?.elements?.map {
                    NearbyBar(
                        it.id,
                        it.tags.get("name").toString(),
                        "whatever"
                    )
                }
            }
        }
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
