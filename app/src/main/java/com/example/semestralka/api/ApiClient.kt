package com.example.semestralka.api

import android.util.Log
import com.example.semestralka.BASE_URL
import com.example.semestralka.data.Bar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    public val barsRequest = retrofit.create(ApiInterface::class.java)

}