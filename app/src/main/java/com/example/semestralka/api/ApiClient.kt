package com.example.semestralka.api

import android.util.Log
import com.example.semestralka.BASE_URL
import com.example.semestralka.data.Bar
import com.example.semestralka.data.PubsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    fun getBars() {
        val request = this.retrofit.create(ApiInterface::class.java)

        val response = request.getData(Request())

        response.enqueue(object : Callback<PubsData?> {
            override fun onResponse(call: Call<PubsData?>, response: Response<PubsData?>) {
                val responseBody = response.body()

                val fetchedBars = mutableListOf<Bar>()

                Log.e("TEST", responseBody.toString())

                responseBody?.documents?.forEach {
                    if (it.tags.name != "") {
                        fetchedBars.add(
                            Bar(
                                it.id?.toInt()!!,
                                it.tags.name
                            )
                        )
                    }
                }
                Log.e("TEST", fetchedBars.toString())
            }

            override fun onFailure(call: Call<PubsData?>, t: Throwable) {

            }
        })
    }

}