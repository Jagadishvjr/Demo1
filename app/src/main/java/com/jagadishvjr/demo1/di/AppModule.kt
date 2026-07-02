package com.jagadishvjr.demo1.di

import com.jagadishvjr.demo1.BuildConfig
import com.jagadishvjr.demo1.data.remote.api.UserApiService
import com.jagadishvjr.demo1.data.repository.UserRepositoryImpl
import com.jagadishvjr.demo1.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient{

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Authorization", BuildConfig.AUTH_TOKEN)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.sportmonks.com/v3/football/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesFixtureApiService(retrofit: Retrofit): UserApiService{
        return retrofit.create(UserApiService::class.java)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindsRepository(impl: UserRepositoryImpl): UserRepository
}
