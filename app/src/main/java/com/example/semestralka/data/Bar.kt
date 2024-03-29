package com.example.semestralka.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bar")
data class Bar(
    @PrimaryKey()
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String
){}