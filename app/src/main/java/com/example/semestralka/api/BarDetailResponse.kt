package com.example.semestralka.api

data class BarDetailItemResponse(
    val type: String,
    val id: String,
    val lat: Double,
    val lon: Double,
    val tags: Map<String, String>
)

data class BarDetailResponse(
    val elements: List<BarDetailItemResponse>
)
