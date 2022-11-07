package com.example.semestralka.viewmodel

import androidx.lifecycle.*
import com.example.semestralka.data.Bar
import com.example.semestralka.data.BarDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BarViewModel(private val barDao: BarDao): ViewModel() {
//    val bars = MutableLiveData<MutableList<Bar>>()
    val bars: LiveData<List<Bar>> = barDao.getBars().asLiveData()

    init {
        viewModelScope.launch {
            addBars()

        }

    }

    suspend fun addBars() {
        withContext(Dispatchers.IO) {
            barDao.insertAll(listOf(
                Bar(
                    123,
                    "super bar 2"
                ),
                Bar(
                    1233,
                    "super bar 3"
                ),
                Bar(
                    1234,
                    "super bar 1"
                )
            ))
        }
    }

    fun retrieveItem(id: Int): LiveData<Bar> {
        return barDao.getBar(id).asLiveData()
    }


//    private fun loadBars() {
//        val retrofitBuilder = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(ApiInterface::class.java)
//
//        val retrofitData = retrofitBuilder.getData(Request())
//
//        retrofitData.enqueue(object : Callback<PubsData?> {
//            override fun onResponse(call: Call<PubsData?>, response: Response<PubsData?>) {
//                val responseBody = response.body()
//
//                val fetchedBars = mutableListOf<Bar>()
//
//                Log.e("TEST", responseBody.toString())
//
//                responseBody?.documents?.forEach {
//                    if (it.tags.name != "") {
//                        fetchedBars.add(
//                            Bar(
//                                it.tags.name
//                            )
//                        )
//                    }
//                }
//                bars.value = fetchedBars
//                Log.e("TEST", bars.toString())
//            }
//
//            override fun onFailure(call: Call<PubsData?>, t: Throwable) {
//
//            }
//        })
////        val listOfBars =
//
////        _bars = listOfBars
//    }

//    fun order() {
//        bars.value = bars.value?.sortedWith(compareBy({it.name}))?.toMutableList()
//    }

    fun deleteBar(bar: Bar) {
        viewModelScope.launch {
            barDao.delete(bar)
        }
    }

//    fun findByName(name: String): Bar? {
//        return bars.value?.find {
//            it.name == name
//        }
//    }


    private fun getMyData() {

    }
}

class BarViewModelFactory(private val barDao: BarDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarViewModel(barDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
