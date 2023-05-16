package com.example.jokes.data.datasource.remote.api

import com.example.jokes.data.datasource.remote.JokeDataEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApiService {
    @GET("api")
    suspend fun getJoke(@Query("format") format:String = "json") : Response<JokeDataEntity>
}