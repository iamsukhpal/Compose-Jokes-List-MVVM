package com.example.jokes.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jokes.data.datasource.local.entities.JokeRoomDataEntity

@Database(entities = [JokeRoomDataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokesDao(): JokesDao
}