package com.tlopez.tello_controller.di

import com.tlopez.tello_controller.data.repository.SocketServiceProvider
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
    fun provideSocketServiceProvider(): SocketServiceProvider = SocketServiceProvider()
}