package com.jagadishvjr.demo1.di

import com.jagadishvjr.demo1.BuildConfig
import com.jagadishvjr.demo1.core.location.AndroidLocationTracker
import com.jagadishvjr.demo1.core.location.LocationTracker
import com.jagadishvjr.demo1.features.fixtures.data.remote.api.FixtureApiService
import com.jagadishvjr.demo1.features.fixtures.data.repository.FixtureRepositoryImpl
import com.jagadishvjr.demo1.features.fixtures.domain.repository.FixtureRepository
import com.jagadishvjr.demo1.features.users.data.remote.api.UserApiService
import com.jagadishvjr.demo1.features.users.data.repository.UserRepositoryImpl
import com.jagadishvjr.demo1.features.users.domain.repository.UserRepository
import com.jagadishvjr.demo1.features.users.domain.usecase.CalculateDistanceUseCase
import com.jagadishvjr.demo1.features.users.domain.usecase.CalculateDistanceWithLocationApiUseCase
import com.jagadishvjr.demo1.features.users.domain.usecase.DistanceCalculatorUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("users")
    fun providesUsersOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("fixtures")
    fun providesFixturesOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .header("Authorization", BuildConfig.FIXTURE_API_KEY)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("users")
    fun providesUsersRetrofit(@Named("users") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("fixtures")
    fun providesFixturesRetrofit(@Named("fixtures") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.FIXTURE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesUserApiService(@Named("users") retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesFixtureApiService(@Named("fixtures") retrofit: Retrofit): FixtureApiService {
        return retrofit.create(FixtureApiService::class.java)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindsFixtureRepository(impl: FixtureRepositoryImpl): FixtureRepository

    @Binds
    @Singleton
    abstract fun bindsLocationTracker(impl: AndroidLocationTracker): LocationTracker

    @Binds
    @Named("haversineDistance")
    abstract fun bindsHaversineDistanceCalculator(
        impl: CalculateDistanceUseCase
    ): DistanceCalculatorUseCase

    @Binds
    @Named("locationApiDistance")
    abstract fun bindsLocationApiDistanceCalculator(
        impl: CalculateDistanceWithLocationApiUseCase
    ): DistanceCalculatorUseCase
}
