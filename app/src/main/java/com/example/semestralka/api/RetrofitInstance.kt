package com.example.semestralka.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    val client = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    val api: FinalApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://zadanie.mpage.sk/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FinalApi::class.java)
    }
}