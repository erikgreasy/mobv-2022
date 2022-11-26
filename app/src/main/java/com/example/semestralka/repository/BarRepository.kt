package com.example.semestralka.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.semestralka.api.ApiClient
import com.example.semestralka.api.Request
import com.example.semestralka.data.Bar
import com.example.semestralka.data.BarDatabase
import com.example.semestralka.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BarRepository(private val database: BarDatabase) {
    suspend fun refreshBars() {
        withContext(Dispatchers.IO) {
            val retrofitData = ApiClient().barsRequest.getData(Request())
            database.barDao().insertAll(retrofitData.asDomainModel())
//            retrofitData.enqueue(object : Callback<PubsData?> {
//                override fun onResponse(call: Call<PubsData?>, response: Response<PubsData?>) {
//                    val responseBody = response.body()
//
//                    val fetchedBars = mutableListOf<Bar>()
//
//                    responseBody?.documents?.forEach {
//                        if (it.tags.name != "") {
//                            fetchedBars.add(
//                                Bar(
//                                    it.id?.toLong()!!,
//                                    it.tags.name
//                                )
//                            )
//                        }
//                    }
//                    withContext(Dispatchers.IO) {
//                        insertVideos(fetchedBars)
//                    }
//                }
//
//                override fun onFailure(call: Call<PubsData?>, t: Throwable) {
//
//                }
//            })
        }
    }

    suspend fun insertVideos(bars: List<Bar>) {
        withContext(Dispatchers.IO) {
            database.barDao().insertAll(bars)
        }
    }


}