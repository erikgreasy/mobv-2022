package com.example.semestralka.data

import android.util.Log
import com.example.semestralka.model.Bar
import com.google.gson.Gson
import java.io.InputStream

class BarDatasource {
    fun loadBars(inputStream: InputStream): MutableList<Bar> {
        val bar = Gson()
            .fromJson(inputStream.bufferedReader(), PubsData::class.java)

        val pubs = bar.elements
        val bars = mutableListOf<Bar>()

        pubs?.forEach {
            Log.i("DATA", it.toString())
            bars.add(
                Bar(
                    it.tags?.name ?: ""
                )
            )
        }

        return bars
    }
}