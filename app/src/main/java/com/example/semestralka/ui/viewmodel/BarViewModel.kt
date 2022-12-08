package com.example.semestralka.ui.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import com.example.semestralka.BarApplication
import com.example.semestralka.api.RefreshData
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class BarViewModel(val authViewModel: AuthViewModel, val application: BarApplication): ViewModel() {
    val bars = MutableLiveData<List<com.example.semestralka.api.Bar>>(listOf())
    val barDetail = MutableLiveData<NearbyBar?>(null)

    init {
        loadBars()
    }

    fun loadBars() {
        viewModelScope.launch {
            try {

                val response = RetrofitInstance.api.getActiveBars(
                    authViewModel.loggedUser.value?.uid!!,
                    "Bearer " + authViewModel.loggedUser.value?.access!!
                )

                Log.e("BAR VIEW MODEL LOADBARS", response.toString())

                if(!response.isSuccessful) {
                    Log.e("BAR VIEW MODEL LOADBARS", "neuspesny fetch barov")
                    if(response.code() == 401) {
                        val prefrencesData = PreferencesData(application.applicationContext)
                        val refreshToken = prefrencesData.getLoggedUser()?.refresh

                        if(refreshToken == null) {
                            return@launch
                        }

                        val refreshResponse = RetrofitInstance.api.refresh(
                            authViewModel.loggedUser.value?.uid!!,
                            RefreshData(
                                refreshToken
                            )
                        )

                        if(refreshResponse.isSuccessful) {
                            prefrencesData.setLoggedUser(LoggedUser(
                                refreshResponse.body()?.uid!!,
                                refreshResponse.body()?.access!!,
                                refreshResponse.body()?.refresh!!,
                            ))
                        } else {
                            Log.e("ASD", "nepodarilo sa refreshnut")
                            Log.e("ASD", refreshResponse.toString())
                            authViewModel.logout()
                        }

                        return@launch
                    }
                }

                bars.value = response.body()!!
                application.database.appDao().insertBars(
                    response.body()!!.map {
                        BarItem(
                            it.bar_id,
                            it.bar_name,
                            "aaa",
                            it.lat.toDouble(),
                            it.lon.toDouble(),
                            it.users.toInt()
                        )
                    }
                )
            } catch(e: IOException) {
                e.printStackTrace()
                Log.e("fetching bars", "IO exception")
                bars.value = application.database.appDao().getBars().value?.map {
                    com.example.semestralka.api.Bar(
                        it.id,
                        it.name,
                        it.lat.toString(),
                        it.lon.toString(),
                        it.users.toString()
                    )
                }
            } catch(e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("fetching bars", "general exception")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getBar(id: String) {
        viewModelScope.launch {
            Log.e("bar detail", "start request")
            val q = "[out:json];node($id);out body;>;out skel;"
            val response = RetrofitInstance.api.barDetail(q)

            if(!response.isSuccessful) {
                Log.e("bar detail", "request not successful")

                return@launch
            }

            if(response.body()!!.elements.isEmpty()) {
                Log.e("bar detail", "elements empty")

                return@launch
            }

            Log.e("bar detail", "request successful")

            Log.e("bar detail", response.body().toString())
            val bar = response.body()!!.elements.get(0)

            barDetail.value = NearbyBar(
                bar.id,
                bar.tags.getOrDefault("name", ""),
                bar.tags.getOrDefault("amenity", ""),
                bar.lat,
                bar.lon,
                bar.tags
            )
            application
        }
    }
}

class BarViewModelFactory(private val authViewModel: AuthViewModel, private val application: BarApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarViewModel(authViewModel, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
