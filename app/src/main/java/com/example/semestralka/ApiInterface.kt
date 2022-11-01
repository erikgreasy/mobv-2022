package com.example.semestralka

import com.example.semestralka.data.DataItem
import com.example.semestralka.data.PubsData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("api-key: KHUu1Fo8042UwzczKz9nNeuVOsg2T4ClIfhndD2Su0G0LHHCBf0LnUF05L231J0M")
    @POST("action/find")
    fun getData(@Body request: Request): Call<PubsData>
}