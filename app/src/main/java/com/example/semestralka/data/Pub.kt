package com.example.semestralka.data

data class Pub(
    val type : String? = null,
    val id : String? = null,
    val lat : String?,
    val lon : String? = null,
    val tags : Tags
) {
}