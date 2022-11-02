package com.example.semestralka.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka.ApiInterface
import com.example.semestralka.BASE_URL
import com.example.semestralka.R
import com.example.semestralka.Request
import com.example.semestralka.data.BarDatasource
import com.example.semestralka.data.PubsData
import com.example.semestralka.model.Bar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BarViewModel: ViewModel() {
    val bars =  MutableLiveData<MutableList<Bar>>()

    init {
        loadBars()
    }

    private fun loadBars() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData(Request())

        retrofitData.enqueue(object : Callback<PubsData?> {
            override fun onResponse(call: Call<PubsData?>, response: Response<PubsData?>) {
                val responseBody = response.body()

                val fetchedBars = mutableListOf<Bar>()

                Log.e("TEST", responseBody.toString())

                responseBody?.documents?.forEach {
                    if (it.tags.name != "") {
                        fetchedBars.add(
                            Bar(
                                it.tags.name
                            )
                        )
                    }
                }
                bars.value = fetchedBars
                Log.e("TEST", bars.toString())
            }

            override fun onFailure(call: Call<PubsData?>, t: Throwable) {

            }
        })
//        val listOfBars =

//        _bars = listOfBars
    }

    fun order() {
        bars.value = bars.value?.sortedWith(compareBy({it.name}))?.toMutableList()
    }

    fun deleteBar(bar: Bar) {
        bars.value?.remove(bar)

//        _bars.remove(item)
    }

    fun findByName(name: String): Bar? {
        return bars.value?.find {
            it.name == name
        }
    }


    private fun getMyData() {

    }
}

class BarViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
