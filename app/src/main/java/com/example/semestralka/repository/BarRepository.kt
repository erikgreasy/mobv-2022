package com.example.semestralka.repository

import com.example.semestralka.api.Request
import com.example.semestralka.data.Bar
import com.example.semestralka.data.BarDatabase
import com.example.semestralka.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BarRepository(private val database: BarDatabase) {
    suspend fun refreshBars() {
        withContext(Dispatchers.IO) {
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