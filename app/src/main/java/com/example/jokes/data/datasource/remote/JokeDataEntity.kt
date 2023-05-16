package com.example.jokes.data.datasource.remote

import com.example.jokes.domain.content.JokeContent
import com.google.gson.annotations.SerializedName

data class JokeDataEntity(@SerializedName("joke") val joke: String){
    fun toContent(): JokeContent {
        return JokeContent(joke = joke)
    }
}
