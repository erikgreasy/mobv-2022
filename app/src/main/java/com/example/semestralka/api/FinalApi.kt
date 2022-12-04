package com.example.semestralka.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response
import retrofit2.http.*

interface FinalApi {

    @GET("bar/list.php")
    @Headers(
        "x-apikey: c95332ee022df8c953ce470261efc695ecf3e784",
    )
    suspend fun getActiveBars(
        @Header("x-user")
        user: String,

        @Header("authorization")
        token: String
    ): Response<List<Bar>>

    @POST("user/login.php")
    @Headers(
        "x-apikey: c95332ee022df8c953ce470261efc695ecf3e784",
    )
    suspend fun login(
        @Body
        userData: UserRequest
    ): Response<UserResponse>

    @POST("user/create.php")
    @Headers(
        "x-apikey: c95332ee022df8c953ce470261efc695ecf3e784",
    )
    suspend fun register(
        @Body
        userData: UserRequest
    ): Response<UserResponse>
}