package com.example.semestralka

import android.app.Application
import com.example.semestralka.data.BarDatabase

class BarApplication : Application() {
    val database: BarDatabase by lazy { BarDatabase.getDatabase(this) }
}