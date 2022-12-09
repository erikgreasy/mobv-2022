package com.example.semestralka.api

import android.content.Context
import android.util.Log
import com.example.semestralka.data.PreferencesData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitInstance private constructor(context: Context) {
    private val sharedObject = PreferencesData.getInstance(context)

    val client = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("x-apikey", "c95332ee022df8c953ce470261efc695ecf3e784")
                .addHeader("x-user", sharedObject.getLoggedUser()?.uid.toString())
                .addHeader("authorization", "Bearer " + sharedObject.getLoggedUser()?.access.toString())

            val request = requestBuilder.build()

            val response = chain.proceed(request)

            if(response.code == 401) {
//                api.refresh()
            }
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

    companion object {
        @Volatile
        private var INSTANCE: RetrofitInstance? = null

        fun getInstance(context: Context): RetrofitInstance =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: RetrofitInstance(context).also { INSTANCE = it }
            }
    }
}