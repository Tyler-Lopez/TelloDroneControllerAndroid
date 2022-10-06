package com.tlopez.tello_controller.di

import com.tlopez.tello_controller.data.repository.SocketServiceRepositoryImpl
import com.tlopez.tello_controller.domain.models.SocketServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSocketServiceRepository(): SocketServiceRepository = SocketServiceRepositoryImpl()
}