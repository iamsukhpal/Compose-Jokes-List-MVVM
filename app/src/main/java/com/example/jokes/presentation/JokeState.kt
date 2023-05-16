package com.example.jokes.presentation

sealed class JokeState {
    object START : JokeState()
    object LOADING : JokeState()
    object SUCCESS : JokeState()
    data class FAILURE(val message: String) : JokeState()
}