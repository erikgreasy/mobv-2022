package com.example.semestralka.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface FinalApi {

    @GET("bar/list.php")
    @Headers(
        "x-apikey: c95332ee022df8c953ce470261efc695ecf3e784",
        "x-user: 307",
        "authorization: Bearer 0d82fa39c0831e561202b7978854022b0c011fb0"
    )
    suspend fun getActiveBars(): Response<List<Bar>>
}