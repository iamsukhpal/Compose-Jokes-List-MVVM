package com.example.jokes.domain.content

import com.example.jokes.presentation.model.JokeData

data class JokeContent(val joke:String){
    fun toData():JokeData{
        return JokeData(joke = joke)
    }
}
