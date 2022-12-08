package com.example.semestralka.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response
import retrofit2.http.*

interface FinalApi {

    @GET("bar/list.php")
    suspend fun getActiveBars(
        @Header("x-user")
        user: String,

        @Header("authorization")
        token: String
    ): Response<List<Bar>>

    @POST("user/login.php")
    suspend fun login(
        @Body
        userData: UserRequest
    ): Response<UserResponse>

    @POST("user/refresh.php")
    suspend fun refresh(
        @Header("x-user")
        user: String,

        @Body
        refreshData: RefreshData
    ): Response<UserResponse>


    @POST("user/create.php")
    suspend fun register(
        @Body
        userData: UserRequest
    ): Response<UserResponse>

    /**
     * List of friends that added me in their application.
     * That way i can see where they are.
     */
    @GET("contact/list.php")
    suspend fun getFriends(
        @Header("x-user")
        user: String,

        @Header("authorization")
        token: String
    ): Response<List<User>>

    /**
     * List of friends that I added.
     * They can see my position and i can manage this list.
     */
    @GET("contact/friends.php")
    suspend fun getFriendsAddedByMe(
        @Header("x-user")
        user: String,

        @Header("authorization")
        token: String
    ): Response<List<User>>

    @POST("contact/delete.php")
    suspend fun deleteFriend(
        @Header("x-user")
        user: String,

        @Header("authorization")
        token: String,

        @Body
        friendData: AddFriendRequest
    ): Response<Void>

    @POST("contact/message.php")
    suspend fun addFriend(
        @Header("x-user")
        user: String,

        @Header("authorization")
        token: String,

        @Body
        friendData: AddFriendRequest
    ): Response<Void>

    @GET("https://overpass-api.de/api/interpreter?")
    suspend fun barNearby(@Query("data") data: String): Response<BarDetailResponse>
}