package com.example.semestralka.api

data class User(
    val user_id: String,
    val user_name: String,
    val bar_id: String?,
    val bar_name: String?,
    val time: String?,
    val bar_lat: String?,
    val bar_lon: String?,
)