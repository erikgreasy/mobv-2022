package com.example.semestralka.service

import android.location.Location
import com.example.semestralka.data.MyLocation

class DistanceCounter {
    fun countDistance(location1: MyLocation, location2: MyLocation): Double {
        return Location("").apply {
            latitude = location1.lat
            longitude = location1.lon
        }.distanceTo(Location("").apply {
            latitude = location2.lat
            longitude = location2.lon
        }).toDouble()
    }
}