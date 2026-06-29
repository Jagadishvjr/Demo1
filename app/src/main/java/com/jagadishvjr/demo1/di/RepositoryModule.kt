package com.jagadishvjr.demo1.di

import com.jagadishvjr.demo1.data.repository.UserRepositoryImpl
import com.jagadishvjr.demo1.domin.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun bindsRepository(impl: UserRepositoryImpl): UserRepository
}