package com.tlopez.controllerData.di

import com.tlopez.controllerData.TelloRepositoryImpl
import com.tlopez.controllerData.TelloStateUtil
import com.tlopez.controllerDomain.TelloRepository
import com.tlopez.controllerDomain.TelloState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TelloDataModule {
    @Provides
    @Singleton
    fun provideTelloRepository(telloStateUtil: TelloStateUtil): TelloRepository {
        return TelloRepositoryImpl(telloStateUtil)
    }

    @Provides
    fun provideTelloStateUtil(): TelloStateUtil {
        return TelloStateUtil()
    }
}