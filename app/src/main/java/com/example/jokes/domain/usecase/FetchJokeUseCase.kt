package com.example.jokes.domain.usecase

import com.example.jokes.domain.common.BaseResult
import com.example.jokes.domain.common.Failure
import com.example.jokes.domain.content.JokeContent
import com.example.jokes.domain.repository.JokeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchJokeUseCase @Inject constructor(private val jokeRepository: JokeRepository) {
    suspend fun invoke() : Flow<BaseResult<JokeContent, Failure>> {
        return jokeRepository.getJoke()
    }
}