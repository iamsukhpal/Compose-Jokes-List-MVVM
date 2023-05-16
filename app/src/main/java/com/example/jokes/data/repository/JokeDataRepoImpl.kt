package com.example.jokes.data.repository

import com.example.jokes.data.datasource.local.db.JokesDao
import com.example.jokes.data.datasource.local.entities.JokeRoomDataEntity
import com.example.jokes.data.datasource.remote.JokeRemoteDataSource
import com.example.jokes.domain.common.BaseResult
import com.example.jokes.domain.common.Failure
import com.example.jokes.domain.content.JokeContent
import com.example.jokes.domain.repository.JokeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JokeDataRepoImpl(
    private val jokeRemoteDataSource: JokeRemoteDataSource,
    private val jokeLocalDataSource: JokesDao
) : JokeRepository {
    override suspend fun getJoke(): Flow<BaseResult<JokeContent, Failure>> {
        return flow {
            val localJokes = jokeLocalDataSource.getAll()

            var jokesCount: Int = localJokes?.size ?: 0

            // send the local db value to domain layer 1-1 if not empty
            localJokes?.forEach {
                emit(BaseResult.Success(it.roomDataToEntityData().toContent()))
                delay(1000)
            }

            // fetch jokes from remote if jokes count is less than 10
            while (jokesCount < 10) {
                val result = jokeRemoteDataSource.fetchJoke()
                if (result is BaseResult.Success) {
                    result.data?.let {
                        saveToLocal(JokeRoomDataEntity(0, it.joke))
                        jokesCount += 1
                    }
                }
                emit(result)
                delay(60000)
            }
        }
    }

    private fun saveToLocal(joke: JokeRoomDataEntity) {
        jokeLocalDataSource.insert(joke)
    }
}