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

class BarViewModel(authViewModel: AuthViewModel, val application: Application): ViewModel() {
//    val bars = MutableLiveData<MutableList<Bar>>()
    val bars = MutableLiveData<List<com.example.semestralka.api.Bar>>(listOf())


    init {
        loadBars(authViewModel)
    }

    fun loadBars(authViewModel: AuthViewModel) {
        viewModelScope.launch {
            val response = RetrofitInstance.api.getActiveBars(
                authViewModel.loggedUser.value?.uid!!,
                "Bearer " + authViewModel.loggedUser.value?.access!!
            )

            Log.e("GREASY", response.toString())

            if(!response.isSuccessful) {
                Log.e("ASD", "neuspesny fetch barov")
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

//    private fun loadBars() {
//        val retrofitBuilder = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(ApiInterface::class.java)
//
//        val retrofitData = retrofitBuilder.getData(Request())
//
//        retrofitData.enqueue(object : Callback<PubsData?> {
//            override fun onResponse(call: Call<PubsData?>, response: Response<PubsData?>) {
//                val responseBody = response.body()
//
//                val fetchedBars = mutableListOf<Bar>()
//
//                Log.e("TEST", responseBody.toString())
//
//                responseBody?.documents?.forEach {
//                    if (it.tags.name != "") {
//                        fetchedBars.add(
//                            Bar(
//                                it.tags.name
//                            )
//                        )
//                    }
//                }
//                bars.value = fetchedBars
//                Log.e("TEST", bars.toString())
//            }
//
//            override fun onFailure(call: Call<PubsData?>, t: Throwable) {
//
//            }
//        })
////        val listOfBars =
//
////        _bars = listOfBars
//    }

//    fun order() {
//        bars.value = bars.value?.sortedWith(compareBy({it.name}))?.toMutableList()
//    }
//        return bars.value?.find {
//            it.name == name
//        }
//    }


    private fun getMyData() {

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
