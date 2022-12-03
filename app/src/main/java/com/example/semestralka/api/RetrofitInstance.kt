package com.example.semestralka.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: FinalApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://zadanie.mpage.sk/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FinalApi::class.java)
    }
}