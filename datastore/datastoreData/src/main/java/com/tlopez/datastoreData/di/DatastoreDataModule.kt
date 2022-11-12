package com.tlopez.datastoreData.di

import android.content.Context
import com.tlopez.datastoreData.DatastoreRepositoryImpl
import com.tlopez.datastoreDomain.repository.DatastoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreDataModule {

    @Provides
    @Singleton
    fun provideDatastoreRepository(
        @ApplicationContext context: Context
    ): DatastoreRepository {
        return DatastoreRepositoryImpl(context)
    }
}