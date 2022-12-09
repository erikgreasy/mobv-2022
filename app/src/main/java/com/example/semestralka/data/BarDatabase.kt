package com.example.semestralka.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Bar::class], version = 1, exportSchema = false)
abstract class BarDatabase: RoomDatabase() {
    abstract fun barDao(): BarDao

    companion object {
        @Volatile
        private var INSTANCE: BarDatabase? = null

        fun getDatabase(context: Context): BarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BarDatabase::class.java,
                    "bar_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }
}