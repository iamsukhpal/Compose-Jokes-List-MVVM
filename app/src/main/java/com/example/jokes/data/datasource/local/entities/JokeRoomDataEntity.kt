package com.example.jokes.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.jokes.data.datasource.remote.JokeDataEntity

@Entity(tableName = "jokes", indices = [Index(value = ["id"], unique = true)])
data class JokeRoomDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    @ColumnInfo(name = "joke")
    var joke: String
){

    fun roomDataToEntityData(): JokeDataEntity{
        return JokeDataEntity(joke)
    }
}