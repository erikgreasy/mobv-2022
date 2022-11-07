package com.example.semestralka.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.semestralka.data.Bar
import com.example.semestralka.data.BarDatabase

class BarRepository(private val database: BarDatabase) {
    val bars: LiveData<List<Bar>> = androidx.lifecycle.Transformations.map(database.barDao().getBars().asLiveData()) {
        it
    }

//    suspend fun refreshVideos() {
//        withContext(Dispatchers.IO) {
//            val retrofitData = Network.bars.getData(Request())
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
//                                    it.id?.toInt()!!,
//                                    it.tags.name
//                                )
//                            )
//                        }
//                    }
//                    database.barDao().insertAll(fetchedBars)
//                }
//
//                override fun onFailure(call: Call<PubsData?>, t: Throwable) {
//
//                }
//            })
//        }
//    }
}