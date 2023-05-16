package com.example.jokes.data.datasource.local.db

import androidx.room.*
import com.example.jokes.data.datasource.local.entities.JokeRoomDataEntity

@Dao
interface JokesDao {

    @Query("SELECT * FROM jokes")
    fun getAll(): List<JokeRoomDataEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(joke: JokeRoomDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(jokes: List<JokeRoomDataEntity>)

    @Delete
    fun delete(joke: JokeRoomDataEntity)

    @Query("DELETE FROM jokes")
    fun deleteAll()
}