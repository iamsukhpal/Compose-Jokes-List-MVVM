package com.example.jokes.data.di

import com.example.jokes.data.datasource.local.db.AppDatabase
import com.example.jokes.data.datasource.local.db.JokesDao
import com.example.jokes.data.datasource.remote.JokeRemoteDataSource
import com.example.jokes.data.datasource.remote.api.JokeApiService
import com.example.jokes.data.repository.JokeDataRepoImpl
import com.example.jokes.domain.repository.JokeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JokeModule {

    @Provides
    @Singleton
    fun provideJokeRemoteApi(retrofit: Retrofit) : JokeApiService {
        return retrofit.create(JokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideJokeRemoteDataSource(jokeApiService: JokeApiService) : JokeRemoteDataSource {
        return JokeRemoteDataSource(jokeApiService)
    }

    @Provides
    @Singleton
    fun provideJokesDao(appDatabase: AppDatabase) : JokesDao {
        return appDatabase.jokesDao()
    }

    @Provides
    @Singleton
    fun provideJokeRepository(jokeRemoteDataSource: JokeRemoteDataSource, jokesDao: JokesDao) : JokeRepository {
        return JokeDataRepoImpl(jokeRemoteDataSource, jokesDao)
    }
}