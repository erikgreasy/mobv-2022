package com.example.semestralka

import android.app.Application
import com.example.semestralka.data.AppRoomDatabase
import com.example.semestralka.data.BarDatabase

class BarApplication : Application() {
    val database: AppRoomDatabase by lazy { AppRoomDatabase.getInstance(this) }
}