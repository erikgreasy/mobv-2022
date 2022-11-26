package com.example.semestralka.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiBarContainer(val videos: List<Pub>)

@JsonClass(generateAdapter = true)
data class Pub(
    val type : String? = null,
    val id : String? = null,
    val lat : String?,
    val lon : String? = null,
    val tags : Tags)

fun ApiBarContainer.asDomainModel(): List<com.example.semestralka.data.Bar> {
    return videos.map {
        com.example.semestralka.data.Bar(
            id = it.id?.toLong()!!,
            name = it.tags.name)
    }
}
