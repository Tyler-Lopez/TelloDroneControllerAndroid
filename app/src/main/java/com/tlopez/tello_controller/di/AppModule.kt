package com.tlopez.tello_controller.di

import com.tlopez.tello_controller.data.repository.TelloRepositoryImpl
import com.tlopez.tello_controller.domain.models.TelloRepository
import com.tlopez.tello_controller.util.TelloStateUtil
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
    fun provideSocketServiceRepository(telloStateUtil: TelloStateUtil): TelloRepository =
        TelloRepositoryImpl(telloStateUtil)

    @Provides
    fun provideTelloStateUtil(): TelloStateUtil = TelloStateUtil()
}