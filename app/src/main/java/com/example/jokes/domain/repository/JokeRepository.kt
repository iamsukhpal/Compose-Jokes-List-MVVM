package com.example.jokes.domain.repository

import com.example.jokes.domain.common.BaseResult
import com.example.jokes.domain.common.Failure
import com.example.jokes.domain.content.JokeContent
import kotlinx.coroutines.flow.Flow

interface JokeRepository {
    suspend fun getJoke() : Flow<BaseResult<JokeContent, Failure>>
}