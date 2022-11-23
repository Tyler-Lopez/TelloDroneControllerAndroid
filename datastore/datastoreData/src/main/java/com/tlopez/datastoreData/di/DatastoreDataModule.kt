package com.tlopez.datastoreData.di

import com.tlopez.datastoreData.DatastoreRepositoryImpl
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreDataModule {

    @Provides
    @Singleton
    fun provideDatastoreRepository(): DatastoreRepository {
        return DatastoreRepositoryImpl()
    }
}