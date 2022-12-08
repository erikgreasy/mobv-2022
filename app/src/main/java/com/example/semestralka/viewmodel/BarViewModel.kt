package com.example.semestralka.viewmodel

import android.app.Application
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.*
import com.example.semestralka.api.RefreshData
import com.example.semestralka.api.RetrofitInstance
import com.example.semestralka.data.*
import com.example.semestralka.repository.BarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BarViewModel(val authViewModel: AuthViewModel, val application: Application): ViewModel() {
    val bars = MutableLiveData<List<com.example.semestralka.api.Bar>>(listOf())

    init {
        loadBars()
    }

    fun loadBars() {
        viewModelScope.launch {
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
        }
    }

    fun getBar(id: String): com.example.semestralka.api.Bar? {
        val filteredBars = this.bars.value?.filter {
            id == it.bar_id
        }

        if(!filteredBars!!.isEmpty()) {
            return filteredBars[0]
        }

        return null
    }
}

class BarViewModelFactory(private val authViewModel: AuthViewModel, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarViewModel(authViewModel, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
