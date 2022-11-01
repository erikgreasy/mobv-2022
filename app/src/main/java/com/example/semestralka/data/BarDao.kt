package com.example.semestralka.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bar: Bar)

    @Query("SELECT * FROM bar")
    fun getBars(): Flow<List<Bar>>
}