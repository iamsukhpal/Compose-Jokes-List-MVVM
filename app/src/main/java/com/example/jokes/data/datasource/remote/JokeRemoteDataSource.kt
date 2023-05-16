package com.example.jokes.data.datasource.remote

import com.example.jokes.data.datasource.network.NoInternetConnectionException
import com.example.jokes.data.datasource.remote.api.JokeApiService
import com.example.jokes.domain.common.BaseResult
import com.example.jokes.domain.common.Failure
import com.example.jokes.domain.content.JokeContent
import java.lang.Exception
import javax.inject.Inject

class JokeRemoteDataSource @Inject constructor(private val jokeApiService: JokeApiService) {
    suspend fun fetchJoke(): BaseResult<JokeContent, Failure> {
        return try {
            val response = jokeApiService.getJoke()
            if (response.isSuccessful) {
                BaseResult.Success(response.body()?.toContent())
            } else {
                //since we don't know the error response of api,
                //i will create a basic error here
                BaseResult.Error(Failure(response.code(), response.message()))
            }
        } catch (e: NoInternetConnectionException) {
            BaseResult.Error(Failure(0, e.message))
        } catch (e: Exception) {
            BaseResult.Error(Failure(-1, e.message.toString()))
        }
    }
}