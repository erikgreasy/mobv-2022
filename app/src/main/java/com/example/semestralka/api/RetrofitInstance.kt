package com.example.semestralka.api

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    val client = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .header("x-apikey", "c95332ee022df8c953ce470261efc695ecf3e784")

            val request = requestBuilder.build()

            val response = chain.proceed(request)

            if(response.code == 500) {
                Log.e("POKUS O INTERCEPTOR", "mame status 500")
            }

            response
        }
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