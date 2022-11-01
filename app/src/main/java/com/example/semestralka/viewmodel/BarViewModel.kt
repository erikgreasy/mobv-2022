package com.example.semestralka.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BarViewModel: ViewModel() {
    private lateinit var _bars: MutableList<String>
    val bars: List<String> get() = _bars

    init {
        loadBars()
    }

    private fun loadBars() {
        val listOfBars = mutableListOf<String>("1 bar", "3 bar", "2 bar")

        _bars = listOfBars
    }

    fun order() {
        _bars.sort()
    }

    fun deleteBar(item: String) {
        _bars.remove(item)
    }
}

class BarViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
