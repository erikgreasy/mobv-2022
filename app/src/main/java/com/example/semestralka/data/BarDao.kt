package com.example.semestralka.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bar: Bar)

    @Query("SELECT * from bar WHERE id = :id")
    fun getBar(id: Long): Flow<Bar>

    @Query("SELECT * FROM bar")
    fun getBars(): Flow<List<Bar>>

    @Delete
    suspend fun delete(bar: Bar)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<Bar>)
}