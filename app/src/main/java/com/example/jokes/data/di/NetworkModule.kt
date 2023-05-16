package com.example.jokes.data.di

import android.content.Context
import com.example.jokes.BuildConfig
import com.example.jokes.data.datasource.network.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor;
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(networkInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BuildConfig.API_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
    }

    @Provides
    fun provideInterceptor(@ApplicationContext context: Context) : NetworkInterceptor {
        return NetworkInterceptor(context)
    }
}