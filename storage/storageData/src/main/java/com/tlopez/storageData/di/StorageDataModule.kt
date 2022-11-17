package com.tlopez.storageData.di

import android.content.Context
import com.tlopez.storageData.StorageRepositoryImpl
import com.tlopez.storageDomain.repository.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageDataModule {

    @Provides
    @Singleton
    fun provideDatastoreRepository(
        @ApplicationContext context: Context
    ): StorageRepository {
        return StorageRepositoryImpl(context)
    }
}