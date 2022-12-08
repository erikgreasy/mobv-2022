package com.example.semestralka.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.lifecycle.*
import com.example.semestralka.api.BarDetailItemResponse
import com.example.semestralka.api.CheckinBarRequest
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.data.Bar
import com.example.semestralka.data.MyLocation
import com.example.semestralka.data.NearbyBar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.jar.Manifest


class LocateViewModel(val activity: Activity, val authViewModel: AuthViewModel): ViewModel() {
    val loading = MutableLiveData(false)

    var location: MyLocation? = null
    val bars = MutableLiveData<List<NearbyBar>>(listOf())
    private val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    val nearBarToCheckin = MutableLiveData<NearbyBar?>(null)

    fun loadBars() {
        viewModelScope.launch {


            val lat = location?.lat
            val lon = location?.lon

            Log.e("LOADING BARS", "with lat: $lat, lon: $lon")

            val q = "[out:json];node(around:250,$lat,$lon);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;"

            val response = RetrofitInstance.api.barNearby(q)

            Log.e("locate model", response.toString())

            if(response.isSuccessful) {
                bars.value = response.body()?.elements?.map {
                    NearbyBar(
                        it.id,
                        it.tags.get("name").toString(),
                        it.type,
                        it.lat,
                        it.lon,
                        it.tags
                    )
                }

                nearBarToCheckin.value = bars.value?.first()
            }
        }
    }

    fun checkinToBar(): LiveData<Boolean?> {
        val success = MutableLiveData<Boolean?>(false)

        try {
            viewModelScope.launch {
                val response = RetrofitInstance.api.checkinToBar(
                    authViewModel.loggedUser.value?.uid!!,
                    "Bearer " + authViewModel.loggedUser.value?.access!!,
                    CheckinBarRequest(
                        nearBarToCheckin.value!!.id,
                        nearBarToCheckin.value!!.name,
                        nearBarToCheckin.value!!.type,
                        nearBarToCheckin.value!!.lat,
                        nearBarToCheckin.value!!.lon,
                    )
                )

                Log.e("checking to bar", response.toString())
                Log.e("checking to bar", response.body().toString())

                if(!response.isSuccessful) {
                    return@launch
                }

                success.postValue(true)

//                Toast.makeText(activity, "Úspešne nahlásený", Toast.LENGTH_SHORT).show()
            }
        } catch(e: IOException) {
            e.printStackTrace()
        } catch(e: java.lang.Exception) {
            e.printStackTrace()
        }

        return success
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {
            if(it == null) {
                Toast.makeText(activity, "Sorry cant get location", Toast.LENGTH_SHORT).show()
            }

            it.apply {
                location = MyLocation(it.latitude, it.longitude)

                loadBars()
            }
        }
    }
}

class LocateViewModelFactory(val activity: Activity, val authViewModel: AuthViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocateViewModel(activity, authViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
