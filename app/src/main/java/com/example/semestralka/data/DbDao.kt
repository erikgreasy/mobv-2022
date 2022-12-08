package com.example.semestralka.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBars(bars: List<BarItem>)

    @Query("DELETE FROM bars")
    suspend fun deleteBars()

    @Query("SELECT * FROM bars order by users DESC, name ASC")
    fun getBars(): LiveData<List<BarItem>?>
}