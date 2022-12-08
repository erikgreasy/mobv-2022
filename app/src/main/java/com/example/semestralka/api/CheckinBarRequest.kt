package com.example.semestralka.api

data class CheckinBarRequest(
    val id: String,
    val name: String,
    val type: String,
    val lat: Double,
    val lon: Double,
)
