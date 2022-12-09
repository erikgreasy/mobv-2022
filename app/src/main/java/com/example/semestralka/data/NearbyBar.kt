package com.example.semestralka.data

data class NearbyBar(
    val id: String,
    val name: String,
    val type: String,
    val lat: Double,
    val lon: Double,
    val tags: Map<String, String>,
)
